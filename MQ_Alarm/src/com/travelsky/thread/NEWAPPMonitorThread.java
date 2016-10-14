package com.travelsky.thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javazoom.jl.player.Player;

import org.apache.log4j.Logger;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

import dcsiclear.thread.IBMMQCfg;
import dcsiclear.thread.NEWAPPIBMMQCfg;

public class NEWAPPMonitorThread extends Thread {

	private static Logger logger = Logger.getLogger(NEWAPPMonitorThread.class);

	public static int inteval = 20000; //监测时间间隔,默认60秒
	
	public static int anjdepthLimit = 100; //安检MQ队列深度报警值,默认500
	public static int anjcurrentDepth = 0; //安检当前队列深度，默认0
	public static int atscdepthLimit = 100; //航延MQ队列深度报警值,默认500
	public static int atsccurrentDepth = 0; //航延当前队列深度，默认0
	public static int pnrdepthLimit = 100; //订座MQ队列深度报警值,默认500
	public static int pnrcurrentDepth = 0; //订座当前队列深度，默认0
	public static int psrdepthLimit = 100; //旅客MQ队列深度报警值,默认500
	public static int psrcurrentDepth = 0; //旅客当前队列深度，默认0
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String queryTime = df.format(new Date());
	//private final String PROP_PATH = "\\setting.properties";
	private final String AUDIO_FILE_NAME = "alarm.mp3";
	private String audioFilePath;
	
	private Player player;
	private boolean stop = false;

	private NEWAPPIBMMQCfg newAPPIbmMQCfg;
	private static MQQueueManager queueManager;
	private static MQQueue anjQueue;
	private static MQQueue atscQueue;
	private static MQQueue pnrQueue;
	private static MQQueue psrQueue;
	private static boolean inited = false;
	
	@Override
	public void run() {
		logger.info("[ NEWAPP ] Start thread...");
		loadProp(); //加载报警的音频配置信息
		logger.info("[ NEWAPP ] 当前配置信息：inteval = "+inteval+" ; anjdepthLimit="+anjdepthLimit);
		logger.info("[ NEWAPP ] 当前配置信息：atscdepthLimit = "+atscdepthLimit+" ; pnrdepthLimit="+pnrdepthLimit);
		logger.info("[ NEWAPP ] 当前配置信息：psrdepthLimit = "+psrdepthLimit);
		while(!stop){ // 循环获取队列深度
			//logger.info("监控线程启动...");
			try {
				if (!inited) {
					inited = init(); //初始化MQ
					logger.info("[ NEWAPP ] MQ inited success...");
				}
				getMQDepth(); //获取当前队列深度
				logger.info("[ NEWAPP ] ANJ depth = " + anjcurrentDepth);
				logger.info("[ NEWAPP ] ATSC depth = " + atsccurrentDepth);
				logger.info("[ NEWAPP ] pnr depth = " + pnrcurrentDepth);
				logger.info("[ NEWAPP ] psr depth = " + psrcurrentDepth);
				if (anjcurrentDepth >= anjdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
				if (atsccurrentDepth >= atscdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
				if (pnrcurrentDepth >= pnrdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
				if (psrcurrentDepth >= psrdepthLimit){
					//队列深度到达报警值，调用报警方法
					alarm();
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			try {
				Thread.sleep(inteval); //睡眠
			} catch (Exception e) {
				logger.error("[ DCSI ] 线程睡眠失败！", e);
				stop = true;
			}
		}
		close();
		logger.info("stop thread...");
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
			audioFilePath = NEWAPPMonitorThread.class.getClassLoader().getResource(AUDIO_FILE_NAME).getPath(); //获取音频路径
			logger.info("[ DCSI ] 音频路径:" + audioFilePath);
/*			Properties prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream(PROP_PATH));
			inteval = Integer.parseInt(prop.getProperty("inteval", "60")) * 1000;
			depthLimit = Integer.parseInt(prop.getProperty("depthLimit", "500"));
			logger.info("读取配置文件成功！inteval="+inteval+"; depthLimit="+depthLimit);*/
		} catch (Exception e){
			logger.error("[ DCSI ] 加载音频路径失败！", e);
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
		MQEnvironment.hostname = newAPPIbmMQCfg.getHostName(); // MQ服务器的IP地址
		MQEnvironment.channel = newAPPIbmMQCfg.getChannel(); // 服务器连接的通道
		// 服务器MQ服务使用的编码1381代表GBK、1208代表UTF(Coded Character Set Identifier:CCSID)
		MQEnvironment.CCSID = newAPPIbmMQCfg.getCcsid();
		MQEnvironment.port = newAPPIbmMQCfg.getPort();// MQ端口
		MQEnvironment.userID = newAPPIbmMQCfg.getClientId();
		int openOptions = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT | CMQC.MQOO_INQUIRE; // Specify default get message options
		try {
			queueManager = new MQQueueManager(newAPPIbmMQCfg.getQueueManager());
			anjQueue = queueManager.accessQueue(newAPPIbmMQCfg.getANJqueueName(), openOptions);
			atscQueue = queueManager.accessQueue(newAPPIbmMQCfg.getATSCqueueName(), openOptions);
			pnrQueue = queueManager.accessQueue(newAPPIbmMQCfg.getPNRqueueName(), openOptions);
			psrQueue = queueManager.accessQueue(newAPPIbmMQCfg.getPSRqueueName(), openOptions);
			inited = true;
		} catch (Exception e) {
			logger.error("[ NEWAPP ] MQ inited exception", e);
			inited = false;
			queryTime = df.format(new Date());
			anjcurrentDepth = -1;
			atsccurrentDepth = -1;
			pnrcurrentDepth = -1;
			psrcurrentDepth = -1;
			return false;
		}
		return true;
	}
	
	/**
	 * 获取MQ深度
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
			anjcurrentDepth = anjQueue.getCurrentDepth();
			atsccurrentDepth = atscQueue.getCurrentDepth();
			pnrcurrentDepth = pnrQueue.getCurrentDepth();
			psrcurrentDepth = psrQueue.getCurrentDepth();
			//logger.info("MQ depth is: " + depth);
			queueManager.commit();
			queryTime = df.format(new Date());
		} catch (Exception e) {
			logger.info("[ NEWAPP ] MQ getDepth exception.", e);
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
			logger.error("[ DCSI ] 播放音频失败！", e);
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
			if (anjQueue != null) {
				anjQueue.close();
			}
			if (atscQueue != null) {
				atscQueue.close();
			}
			if (pnrQueue != null) {
				pnrQueue.close();
			}
			if (psrQueue != null) {
				psrQueue.close();
			}
			logger.info("---------------[ NEWAPP ] close MQ connection---------------");
		} catch (Exception e) {
			logger.error("[ NEWAPP ] MQ close exception...", e);
		}
	}



	public NEWAPPIBMMQCfg getNewAPPIbmMQCfg() {
		return newAPPIbmMQCfg;
	}

	public void setNewAPPIbmMQCfg(NEWAPPIBMMQCfg newAPPIbmMQCfg) {
		this.newAPPIbmMQCfg = newAPPIbmMQCfg;
	}

	public int getInteval() {
		return inteval;
	}

	public void setInteval(int inteval) {
		this.inteval = inteval;
	}

	public  int getAnjdepthLimit() {
		return anjdepthLimit;
	}

	public  void setAnjdepthLimit(int anjdepthLimit) {
		this.anjdepthLimit = anjdepthLimit;
	}

	public  int getAtscdepthLimit() {
		return atscdepthLimit;
	}

	public  void setAtscdepthLimit(int atscdepthLimit) {
		this.atscdepthLimit = atscdepthLimit;
	}

	public  int getPnrdepthLimit() {
		return pnrdepthLimit;
	}

	public  void setPnrdepthLimit(int pnrdepthLimit) {
		this.pnrdepthLimit = pnrdepthLimit;
	}

	public  int getPsrdepthLimit() {
		return psrdepthLimit;
	}

	public  void setPsrdepthLimit(int psrdepthLimit) {
		this.psrdepthLimit = psrdepthLimit;
	}

	
	
}
