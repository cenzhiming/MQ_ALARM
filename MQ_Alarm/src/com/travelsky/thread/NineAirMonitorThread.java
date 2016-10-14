package com.travelsky.thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javazoom.jl.player.Player;

import org.apache.log4j.Logger;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

import dcsiclear.thread.NineAirIBMMQCfg;

public class NineAirMonitorThread extends Thread {

	private static Logger logger = Logger.getLogger(NineAirMonitorThread.class);


	public static int inteval = 20000; //监测时间间隔,默认60秒
	public static int psgdepthLimit = 100; //PSG 的MQ队列深度报警值
	public static int bagdepthLimit = 100; //BAG的MQ队列深度报警值
	public static int psgCurrentDepth = 0; //PSG当前队列深度，默认0
	public static int bagCurrentDepth = 0; //BAG当前队列深度，默认0
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String queryTime = df.format(new Date());
	//private final String PROP_PATH = "\\setting.properties";
	private final String AUDIO_FILE_NAME = "alarm.mp3";
	private String audioFilePath;
	
	private Player player;
	private boolean stop = false;

	private NineAirIBMMQCfg nineairIBMMQCfg;

	private static MQQueueManager queueManager;
	private static MQQueue psgQueue;
	private static MQQueue bagQueue;
	private static boolean inited = false;
	
	@Override
	public void run() {
		logger.info("[ NineAir ] Start thread...");
		loadProp(); //加载报警的音频配置信息
		logger.info("[ NineAir ]MQ队列当前配置信息：inteval = "+inteval+" ; BagDepthLimit = "+psgdepthLimit +" ; PsgDepthLimit = " + bagdepthLimit );
		while(!stop){ // 循环获取队列深度
			//logger.info("监控线程启动...");
			try {
				if (!inited) {
					inited = init(); //初始化MQ
					logger.info("[ NineAir ] MQ inited success...");
				}
				
				//判断PSG队列的深度
				 getMQDepth(); //获取当前队列深度
				logger.info("[ NineAir ]PSG Current depth = " + psgCurrentDepth);
				logger.info("[ NineAir ]BAG Current depth = " + bagCurrentDepth);
				if (psgCurrentDepth >= psgdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
				if (bagCurrentDepth >= bagdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			try {
				Thread.sleep(inteval); //睡眠
			} catch (Exception e) {
				logger.error("线程睡眠失败！", e);
				stop = true;
			}
		}
		close();
		logger.info("[ NineAir ] stop thread...");
	}

	public void stopServer() {
		stop = true;
		interrupt();
	}
	
	/**
	 * 加载音频路径
	 * @throws IOException 
	 */
	private void loadProp(){
		try{
			audioFilePath = NineAirMonitorThread.class.getClassLoader().getResource(AUDIO_FILE_NAME).getPath(); //获取音频路径
			logger.info("[ NineAir ] 音频路径:" + audioFilePath);
		} catch (Exception e){
			logger.error("[ NineAir ] 加载音频路径失败！", e);
			stop = true; //读取配置文件失败，停止应用
		}
	}
	
	/**
	 * 初始化MQ
	 * @return
	 */
	private boolean init() {
		// 设置环境:
		// MQEnvironment中包含控制MQQueueManager对象中的环境的构成的静态变量，MQEnvironment的值的设定会在MQQueueManager的构造函数加载的时候起作用，
		// 因此必须在建立MQQueueManager对象之前设定MQEnvironment中的值.
		MQEnvironment.hostname = nineairIBMMQCfg.getHostName(); // MQ服务器的IP地址
		MQEnvironment.channel = nineairIBMMQCfg.getChannel(); // 服务器连接的通道
		// 服务器MQ服务使用的编码1381代表GBK、1208代表UTF(Coded Character Set Identifier:CCSID)
		MQEnvironment.CCSID = nineairIBMMQCfg.getCcsid();
		MQEnvironment.port = nineairIBMMQCfg.getPort();// MQ端口
//		MQEnvironment.userID = nineairIBMMQCfg.getClientId();
		int openOptions = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT | CMQC.MQOO_INQUIRE; // Specify default get message options
		try {
			queueManager = new MQQueueManager(nineairIBMMQCfg.getQueueManager());
			psgQueue = queueManager.accessQueue(nineairIBMMQCfg.getPsgQueueName(), openOptions);
			bagQueue = queueManager.accessQueue(nineairIBMMQCfg.getBagQueueName(), openOptions);
			inited = true;
		} catch (Exception e) {
			logger.error(" [ NineAir ] MQ inited exception", e);
			inited = false;
			queryTime = df.format(new Date());
			psgCurrentDepth = -1;
			bagCurrentDepth = -1;
			return false;
		}
		return true;
	}
	
	/**
	 * 获取PSG的MQ深度
	 * @return
	 */
	private int getMQDepth() {
		int depth = -1;
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + CMQC.MQGMO_SYNCPOINT;
		gmo.options = gmo.options + CMQC.MQGMO_WAIT; 
		gmo.options = gmo.options + CMQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.waitInterval = 1000; 
		try {
			psgCurrentDepth = psgQueue.getCurrentDepth();
			bagCurrentDepth = bagQueue.getCurrentDepth();
			queueManager.commit();
			queryTime = df.format(new Date());
		} catch (Exception e) {
			logger.info(" [ NineAir ] MQ getDepth exception.", e);
			close();
		}
		return depth;
	}
	
	
	/**
	 * 报警方法
	 */
	private void alarm(){
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(audioFilePath));
			player = new Player(buffer);
			player.play();
			player.close();
		} catch(Exception e){
			logger.error("[ NineAir ]播放音频失败！", e);
		}
	}
	
	
	/**
	 * 关闭MQ连接
	 */
	private static void close() {
		inited = false;
		try {
			if (queueManager != null) {
				queueManager.disconnect();
			}
			if (psgQueue != null) {
				psgQueue.close();
			}
			if (bagQueue != null) {
				bagQueue.close();
			}
			logger.info("---------------[ NineAir ] close MQ connection---------------");
		} catch (Exception e) {
			logger.error("[ NineAir ]MQ close exception...", e);
		}
	}

	public int getInteval() {
		return inteval;
	}

	public void setInteval(int inteval) {
		this.inteval = inteval;
	}

	public static MQQueue getPsgQueue() {
		return psgQueue;
	}

	public static void setPsgQueue(MQQueue psgQueue) {
		NineAirMonitorThread.psgQueue = psgQueue;
	}
	public static int getPsgCurrentDepth() {
		return psgCurrentDepth;
	}
	
	public static void setPsgCurrentDepth(int psgCurrentDepth) {
		NineAirMonitorThread.psgCurrentDepth = psgCurrentDepth;
	}
	
	public static int getBagCurrentDepth() {
		return bagCurrentDepth;
	}
	
	public static void setBagCurrentDepth(int bagCurrentDepth) {
		NineAirMonitorThread.bagCurrentDepth = bagCurrentDepth;
	}

	public static MQQueue getBagQueue() {
		return bagQueue;
	}

	public static void setBagQueue(MQQueue bagQueue) {
		NineAirMonitorThread.bagQueue = bagQueue;
	}

	public NineAirIBMMQCfg getNineairIBMMQCfg() {
		return nineairIBMMQCfg;
	}

	public void setNineairIBMMQCfg(NineAirIBMMQCfg nineairIBMMQCfg) {
		this.nineairIBMMQCfg = nineairIBMMQCfg;
	}

	public  int getPsgdepthLimit() {
		return psgdepthLimit;
	}

	public  void setPsgdepthLimit(int psgdepthLimit) {
		this.psgdepthLimit = psgdepthLimit;
	}

	public  int getBagdepthLimit() {
		return bagdepthLimit;
	}

	public  void setBagdepthLimit(int bagdepthLimit) {
		this.bagdepthLimit = bagdepthLimit;
	}
	


}
