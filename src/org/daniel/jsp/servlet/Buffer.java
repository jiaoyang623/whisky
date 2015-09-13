package org.daniel.jsp.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Buffer extends HttpServlet {
	private static Map<String, String> mBufferMap = new HashMap<String, String>();

	private static final long serialVersionUID = 7081414706416714402L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("act");
		String user = req.getParameter("user");

		if ("get".equals(action)) {
			resp.getWriter().print(mBufferMap.get(user));
		} else {
			mBufferMap.put(user, req.getParameter("content"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
