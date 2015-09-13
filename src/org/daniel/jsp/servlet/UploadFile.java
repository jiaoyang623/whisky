package org.daniel.jsp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.daniel.jsp.model.playerlog.LogHelper;
import org.daniel.jsp.model.playerlog.LogHelper.LogBean;

import com.google.gson.Gson;

public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 569263599875798074L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = Utils.upload(req, resp);
		if (path != null) {
			LogHelper helper = new LogHelper();
			List<LogBean> list = helper.parse(path);
			System.out.println(list);
			Gson gson = new Gson();
			resp.getWriter().print(gson.toJson(list));
		}
	}
}
