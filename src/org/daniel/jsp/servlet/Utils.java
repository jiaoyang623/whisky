package org.daniel.jsp.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Utils {

	public static String upload(HttpServletRequest req, HttpServletResponse resp) {
		String path = null;

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			// 构造一个文件上传处理对象
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// 解析表单中提交的所有文件内容
				RequestContext context = new ServletRequestContext(req);
				Iterator<FileItem> items = upload.parseRequest(context)
						.iterator();
				while (items.hasNext()) {
					FileItem item = items.next();
					if (!item.isFormField()) {
						// 取出上传文件的文件名称
						String name = item.getName();
						// 取得上传文件以后的存储路径
						String fileName = name.substring(
								name.lastIndexOf('\\') + 1, name.length());
						// 上传文件以后的存储路径
						path = "/opt/tomcat/webapps/Whisky/store"
								+ File.separatorChar + fileName;

						// 上传文件
						File uploaderFile = new File(path);
						item.write(uploaderFile);
						// 打印上传成功信息
						resp.setContentType("text/html");
						resp.setCharacterEncoding("UTF-8");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

}
