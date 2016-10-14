package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.MonitorThread;

/**
 * Servlet implementation class QueryDepthLimit
 */
public class QueryDepthLimit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryDepthLimit.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDepthLimit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryDepthLimit ]call servlet QueryDepthLimit, return " + MonitorThread.depthLimit);
		PrintWriter pw = response.getWriter();
		pw.print(MonitorThread.depthLimit);
		pw.flush();
	}

}
