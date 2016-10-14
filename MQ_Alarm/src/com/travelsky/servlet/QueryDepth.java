package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.MonitorThread;

public class QueryDepth extends HttpServlet {

	private static final long serialVersionUID = 7333253329318961550L;
	private static Logger logger = Logger.getLogger(QueryDepth.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		String result;
		if (MonitorThread.currentDepth >= MonitorThread.depthLimit || MonitorThread.currentDepth < 0){
			result = "<label style='color:red'>" + MonitorThread.currentDepth + "</label><br>" + "<label class='time'>(数据刷新时间： " + MonitorThread.queryTime + ")</label>";
		} else {
			result = MonitorThread.currentDepth + "<br>" + "<label class='time'>(数据刷新时间 ：" + MonitorThread.queryTime + ")</label>";
		}
		logger.info("currentDepth: " + MonitorThread.currentDepth + " currentTime: " + MonitorThread.queryTime);
		PrintWriter pw = resp.getWriter();
		pw.print(result);
		pw.flush();
		//pw.flush();
	}

}
