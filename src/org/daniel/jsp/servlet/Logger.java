package org.daniel.jsp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logger extends HttpServlet {

	private static final long serialVersionUID = 8928008162436385544L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act = req.getParameter("act");
		if ("add".equals(act)) {
			add(req, resp);
		} else if ("delete".equals(act)) {
			delete(req, resp);
		} else if ("get".equals(act)) {
			get(req, resp);
		} else if ("list".equals(act)) {
			list(req, resp);
		} else {
			resp.getWriter().print("params error");
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

	}

	private void get(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

	}

	private void list(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
