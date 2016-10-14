package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.MonitorThread;
import com.travelsky.thread.NineAirMonitorThread;

public class QueryBagDepth extends HttpServlet {

	private static final long serialVersionUID = 7333253329318961550L;
	private static Logger logger = Logger.getLogger(QueryBagDepth.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (NineAirMonitorThread.bagCurrentDepth >= NineAirMonitorThread.bagdepthLimit || NineAirMonitorThread.bagCurrentDepth < 0){
			result = "<label style='color:red'>" + NineAirMonitorThread.bagCurrentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + NineAirMonitorThread.queryTime + ")</label>";
		} else {
			result = NineAirMonitorThread.bagCurrentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + NineAirMonitorThread.queryTime + ")</label>";
		}
		logger.info("currentDepth: " + NineAirMonitorThread.bagCurrentDepth + " currentTime: " + NineAirMonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
	}

}
