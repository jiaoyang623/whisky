package org.daniel.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.daniel.jsp.beans.LiveSourceBean;
import org.daniel.jsp.manager.LiveSourceManager;

import com.google.gson.Gson;

public class LiveSource extends HttpServlet {
	private static final long serialVersionUID = -6710683188016291337L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("act");
		if ("add".equals(action)) {
			add(req, resp);
		} else if ("update".equals(action)) {
			update(req, resp);
		} else if ("get".equals(action)) {
			get(req, resp);
		} else if ("getAll".equals(action)) {
			getAll(req, resp);
		} else {
			resp.getWriter().print("action error");
		}
	}

	private void getAll(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			List<LiveSourceBean> list = LiveSourceManager.getInstance()
					.getAll();
			resp.getWriter().print(new Gson().toJson(list));
		} catch (SQLException e) {
			resp.getWriter().print(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void get(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			LiveSourceBean bean = LiveSourceManager.getInstance().get();
			resp.getWriter().print(new Gson().toJson(bean));
		} catch (SQLException e) {
			resp.getWriter().print(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		long id = Long.valueOf(req.getParameter("id"));
		int quality = Integer.valueOf(req.getParameter("quality"));
		LiveSourceBean bean = new LiveSourceBean();

		bean.setId(id);
		bean.setQuality(quality);
		bean.setTime(System.currentTimeMillis());

		PrintWriter out = resp.getWriter();

		try {
			LiveSourceManager.getInstance().update(bean);
			out.print("success");
		} catch (SQLException e) {
			out.print(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String urls = req.getParameter("url");
		String[] array = urls.split("\n");
		List<LiveSourceBean> list = new ArrayList<LiveSourceBean>();
		for (String url : array) {
			if (url != null && url.length() > 0) {
				LiveSourceBean bean = new LiveSourceBean();
				bean.setTime(System.currentTimeMillis());
				bean.setUrl(url);
				list.add(bean);
			}
		}

		PrintWriter out = resp.getWriter();
		try {
			LiveSourceManager.getInstance().add(list);
			out.print("success");
		} catch (SQLException e) {
			out.print(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
