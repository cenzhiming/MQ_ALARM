package com.travelsky.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.travelsky.thread.MonitorThread;
import com.travelsky.thread.NEWAPPMonitorThread;
import com.travelsky.thread.NineAirMonitorThread;

public class MqListener implements ServletContextListener{
	
	private static Logger logger = Logger.getLogger(MqListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		stopServer();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("contextInitialized");
		startServer(arg0);
	}


	public void startServer(ServletContextEvent arg0) {
		try {
			ServletContext servletContext = arg0.getServletContext();
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			//WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
			MonitorThread t = (MonitorThread)ac.getBean("monitorThread");
			t.start();
			NineAirMonitorThread nineAirMonitorThread = (NineAirMonitorThread)ac.getBean("nineAirMonitorThread");
			nineAirMonitorThread.start();
			NEWAPPMonitorThread newappMonitorThread=(NEWAPPMonitorThread) ac.getBean("newappMonitorThread");
			newappMonitorThread.start();
		} catch (Exception ex) {
			logger.error("",ex);
		}
	}

	public void stopServer() {
		logger.info("stop Threads");
		try {
			// TODO 释放资源
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}
}
