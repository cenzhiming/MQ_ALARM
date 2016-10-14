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
 * Servlet implementation class QueryNEWAPPInteval
 */
public class QueryNEWAPPInteval extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryNEWAPPInteval.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryNEWAPPInteval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryNineAirInteval ]call servlet QueryNineAirInteval, return " + NineAirMonitorThread.inteval);
		PrintWriter pw = response.getWriter();
		pw.print((double)NEWAPPMonitorThread.inteval/1000 + "秒");
		pw.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */


}
