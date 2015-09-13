package org.daniel.jsp.model.playerlog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

public class LogHelper {

	public List<LogBean> parse(String path) {
		if (path == null || path.trim().length() == 0) {
			return new ArrayList<LogBean>();
		}

		try {
			ZipUtils.unzipr(new File(path));
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final List<LogBean> list = new ArrayList<LogBean>();

		ZipUtils.forEach(new File(path).getParentFile(),
				new ZipUtils.OnEachListener() {

					@Override
					public void onEach(File file) {
						if (file.getAbsolutePath().endsWith("_playerCrash.log")) {
							System.out.println("scanned: "
									+ file.getAbsolutePath());
							StringBuilder sb = new StringBuilder();
							try {
								FileInputStream fis = new FileInputStream(file);
								byte[] buff = new byte[1024];
								int len = 0;
								while ((len = fis.read(buff)) != -1) {
									sb.append(new String(buff, 0, len));
								}
								String[] logs = sb.toString().split("\n");
								for (String line : logs) {
									if (line.contains("<1001>")) {
										list.add(getBeanFromLine(line));
										break;
									}
								}
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});

		clean(path);
		clean(path + "_dir");

		return list;
	}

	@SuppressWarnings("deprecation")
	private static LogBean getBeanFromLine(String line) {
		LogBean bean = new LogBean();

		bean.errorCode = Integer.valueOf(line.substring(line.indexOf("<") + 1,
				line.indexOf(">")));
		bean.nativeCode = Integer.valueOf(line.substring(line.indexOf(">") + 1,
				line.indexOf(", ")));
		String urlStr = line.substring(line.indexOf(", ") + 2);
		String content = URLDecoder.decode(urlStr);
		String[] array = content.split(",");
		bean.nativeDesc = array[0].trim();
		bean.path = array[1].trim();
		bean.device = array[2].trim();
		bean.rom = array[3].trim();
		System.out.println("getLine: " + line);
		return bean;
	}

	public static void clean(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				ZipUtils.forEach(file, new ZipUtils.OnEachListener() {

					@Override
					public void onEach(File file) {
						file.delete();
					}
				});
				file.delete();
			} else {
				file.delete();
			}
		}
	}

	public static class LogBean {
		int errorCode = 0;
		int nativeCode = 0;
		String nativeDesc = "";
		String path = "";
		String device = "";
		String rom = "";

		@Override
		public String toString() {
			return "LogBean [errorCode=" + errorCode + ", nativeCode="
					+ nativeCode + ", nativeDesc=" + nativeDesc + ", path="
					+ path + ", device=" + device + ", rom=" + rom + "]";
		}

	}
}
