package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.NineAirMonitorThread;

public class QueryPsgDepth extends HttpServlet {

	private static final long serialVersionUID = 7333253329318961550L;
	private static Logger logger = Logger.getLogger(QueryPsgDepth.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (NineAirMonitorThread.psgCurrentDepth >= NineAirMonitorThread.psgdepthLimit || NineAirMonitorThread.psgCurrentDepth < 0){
			result = "<label style='color:red'>" + NineAirMonitorThread.psgCurrentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + NineAirMonitorThread.queryTime + ")</label>";
		} else {
			result = NineAirMonitorThread.psgCurrentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + NineAirMonitorThread.queryTime + ")</label>";
		}
		logger.info("currentDepth: " + NineAirMonitorThread.psgCurrentDepth + " currentTime: " + NineAirMonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
	}

}
