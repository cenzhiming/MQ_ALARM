package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.NineAirMonitorThread;

/**
 * Servlet implementation class QueryDepthLimit
 */
public class QueryNineAirDepthLimit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryNineAirDepthLimit.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryNineAirDepthLimit() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryNineAirDepthLimit ]call servlet QueryNineAirDepthLimit, return " + NineAirMonitorThread.psgdepthLimit);
		PrintWriter pw = response.getWriter();
		pw.print(NineAirMonitorThread.psgdepthLimit);
		pw.flush();
	}

}
