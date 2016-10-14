package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.NEWAPPMonitorThread;

/**
 * Servlet implementation class QueryPSRDepth
 */
public class QueryPSRDepth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryPSRDepth.class);
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (NEWAPPMonitorThread.psrcurrentDepth >= NEWAPPMonitorThread.psrdepthLimit || NEWAPPMonitorThread.psrcurrentDepth < 0){
			result = "<label style='color:red'>" + NEWAPPMonitorThread.psrcurrentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + NEWAPPMonitorThread.queryTime + ")</label>";
		} else {
			result = NEWAPPMonitorThread.psrcurrentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + NEWAPPMonitorThread.queryTime + ")</label>";
		}
		logger.info("psrcurrentDepth: " + NEWAPPMonitorThread.psrcurrentDepth + " currentTime: " + NEWAPPMonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
	}
}
