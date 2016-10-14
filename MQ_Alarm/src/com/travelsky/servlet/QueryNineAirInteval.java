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
 * Servlet implementation class QueryInteval
 */
public class QueryNineAirInteval extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryNineAirInteval.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryNineAirInteval() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryNineAirInteval ]call servlet QueryNineAirInteval, return " + NineAirMonitorThread.inteval);
		PrintWriter pw = response.getWriter();
		pw.print((double)NineAirMonitorThread.inteval/1000 + "秒");
		pw.flush();
	}

}
