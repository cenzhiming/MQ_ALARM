package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.NEWAPPMonitorThread;
import com.travelsky.thread.NineAirMonitorThread;

public class QueryANJDepth extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryANJDepth.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (NEWAPPMonitorThread.anjcurrentDepth >= NEWAPPMonitorThread.anjdepthLimit || NEWAPPMonitorThread.anjcurrentDepth < 0){
			result = "<label style='color:red'>" + NEWAPPMonitorThread.anjcurrentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + NEWAPPMonitorThread.queryTime + ")</label>";
		} else {
			result = NEWAPPMonitorThread.anjcurrentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + NEWAPPMonitorThread.queryTime + ")</label>";
		}
		logger.info("currentDepth: " + NEWAPPMonitorThread.anjcurrentDepth + " currentTime: " + NEWAPPMonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
	}
}
