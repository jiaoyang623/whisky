package org.daniel.jsp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.daniel.java.test.xstm.XstmMaker;

public class Xstm extends HttpServlet {

	private static final long serialVersionUID = 7834849803616371473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String xstm = req.getParameter("xstm");
		if (xstm != null) {
			String result = XstmMaker.request(xstm.trim());
			resp.getWriter().print(result);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
