package org.daniel.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Alpha extends HttpServlet {
	private static final long serialVersionUID = 3459940904411630726L;
	private static Map<String, String> mUrlMap = new HashMap<String, String>();
	private static final String DEFAULT_USER = "default";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGet");
		String url = req.getParameter("url");
		String user = req.getParameter("user");
		if (user == null || user.trim().length() == 0) {
			user = DEFAULT_USER;
		}
		System.out.println("Url: " + url + "\nUser: " + user);
		if (url != null && url.length() > 0) {
			// submit
			mUrlMap.put(user, url);
		}

		PrintWriter out = resp.getWriter();
		out.println(mUrlMap.get(user));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
