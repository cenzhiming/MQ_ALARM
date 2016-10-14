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

public class MonitorThread extends Thread {

	private static Logger logger = Logger.getLogger(MonitorThread.class);

	public static int inteval = 20000; //监测时间间隔,默认60秒
	public static int depthLimit = 100; //MQ队列深度报警值,默认500
	public static int currentDepth = 0; //当前队列深度，默认0
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String queryTime = df.format(new Date());
	//private final String PROP_PATH = "\\setting.properties";
	private final String AUDIO_FILE_NAME = "alarm.mp3";
	private String audioFilePath;
	
	private Player player;
	private boolean stop = false;

	private IBMMQCfg ibmMQCfg;
	private static MQQueueManager queueManager;
	private static MQQueue queue;
	private static boolean inited = false;
	
	@Override
	public void run() {
		logger.info("[ DCSI ] Start thread...");
		loadProp(); //加载报警的音频配置信息
		logger.info("[ DCSI ] 当前配置信息：inteval = "+inteval+" ; depthLimit="+depthLimit);
		while(!stop){ // 循环获取队列深度
			//logger.info("监控线程启动...");
			try {
				if (!inited) {
					inited = init(); //初始化MQ
					logger.info("[ DCSI ] MQ inited success...");
				}
				currentDepth = getMQDepth(); //获取当前队列深度
				logger.info("[ DCSI ] Current depth = " + currentDepth);
				if (currentDepth >= depthLimit){
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
			audioFilePath = MonitorThread.class.getClassLoader().getResource(AUDIO_FILE_NAME).getPath(); //获取音频路径
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
		MQEnvironment.hostname = ibmMQCfg.getHostName(); // MQ服务器的IP地址
		MQEnvironment.channel = ibmMQCfg.getChannel(); // 服务器连接的通道
		// 服务器MQ服务使用的编码1381代表GBK、1208代表UTF(Coded Character Set Identifier:CCSID)
		MQEnvironment.CCSID = ibmMQCfg.getCcsid();
		MQEnvironment.port = ibmMQCfg.getPort();// MQ端口
		MQEnvironment.userID = ibmMQCfg.getClientId();
		int openOptions = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT | CMQC.MQOO_INQUIRE; // Specify default get message options
		try {
			queueManager = new MQQueueManager(ibmMQCfg.getQueueManager());
			queue = queueManager.accessQueue(ibmMQCfg.getQueueName(), openOptions);
			inited = true;
		} catch (Exception e) {
			logger.error("[ DCSI ] MQ inited exception", e);
			inited = false;
			queryTime = df.format(new Date());
			currentDepth = -1;
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
			depth = queue.getCurrentDepth();
			//logger.info("MQ depth is: " + depth);
			queueManager.commit();
			queryTime = df.format(new Date());
		} catch (Exception e) {
			logger.info("[ DCSI ] MQ getDepth exception.", e);
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
			if (queue != null) {
				queue.close();
			}
			logger.info("---------------[ DCSI ] close MQ connection---------------");
		} catch (Exception e) {
			logger.error("[ DCSI ] MQ close exception...", e);
		}
	}

	public IBMMQCfg getIbmMQCfg() {
		return ibmMQCfg;
	}

	public void setIbmMQCfg(IBMMQCfg ibmMQCfg) {
		this.ibmMQCfg = ibmMQCfg;
	}

	public int getInteval() {
		return inteval;
	}

	public void setInteval(int inteval) {
		this.inteval = inteval;
	}

	public int getDepthLimit() {
		return depthLimit;
	}

	public void setDepthLimit(int depthLimit) {
		this.depthLimit = depthLimit;
	}
	
}
