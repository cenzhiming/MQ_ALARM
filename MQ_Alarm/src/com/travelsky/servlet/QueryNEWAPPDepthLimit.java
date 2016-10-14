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
 * Servlet implementation class QueryNEWAPPDepthLimit
 */
public class QueryNEWAPPDepthLimit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryNEWAPPDepthLimit.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryNEWAPPDepthLimit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryNineAirDepthLimit ]call servlet QueryNineAirDepthLimit, return " + NineAirMonitorThread.psgdepthLimit);
		PrintWriter pw = response.getWriter();
		pw.print(NEWAPPMonitorThread.anjdepthLimit);
		pw.flush();
	}

	
}
