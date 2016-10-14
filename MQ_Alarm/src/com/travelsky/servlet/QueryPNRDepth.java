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
 * Servlet implementation class QueryPNRDepth
 */
public class QueryPNRDepth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryPNRDepth.class);
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (NEWAPPMonitorThread.pnrcurrentDepth >= NEWAPPMonitorThread.pnrdepthLimit || NEWAPPMonitorThread.pnrcurrentDepth < 0){
			result = "<label style='color:red'>" + NEWAPPMonitorThread.pnrcurrentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + NEWAPPMonitorThread.queryTime + ")</label>";
		} else {
			result = NEWAPPMonitorThread.pnrcurrentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + NEWAPPMonitorThread.queryTime + ")</label>";
		}
		logger.info("pnrcurrentDepth: " + NEWAPPMonitorThread.pnrcurrentDepth + " currentTime: " + NEWAPPMonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
	}
}
