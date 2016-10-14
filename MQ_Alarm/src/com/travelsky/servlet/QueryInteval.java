package com.travelsky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.travelsky.thread.MonitorThread;

/**
 * Servlet implementation class QueryInteval
 */
public class QueryInteval extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryInteval.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryInteval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
//		logger.info("[ QueryInteval ]call servlet QueryInteval, return " + MonitorThread.inteval);
		PrintWriter pw = response.getWriter();
		pw.print((double)MonitorThread.inteval/1000 + "秒");
		pw.flush();
	}

}
