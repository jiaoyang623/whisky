package org.daniel.jsp.model.playerlog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtils {
	public static String unzip(final String path) throws ZipException,
			IOException {
		File file = new File(path);
		ZipFile zipFile = new ZipFile(file);
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(
				file));
		ZipEntry zipEntry = null;
		String folderPath = path + "_dir" + File.separator;

		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String filePath = folderPath + zipEntry.getName();
			System.out.println(filePath);
			if (zipEntry.isDirectory()) {
				unzipDir(filePath);
			} else {
				unzipFile(zipFile, zipEntry, filePath);
			}
		}

		return folderPath;
	}

	public static String unzip(final File file) throws ZipException,
			IOException {
		if (file != null && file.exists()) {
			return unzip(file.getAbsolutePath());
		} else {
			return null;
		}
	}

	private static void unzipFile(ZipFile zipFile, ZipEntry zipEntry,
			String filePath) throws FileNotFoundException, IOException {
		File tmp = new File(filePath);
		if (!tmp.getParentFile().exists()) {
			tmp.getParentFile().mkdirs();
		}
		OutputStream os = new FileOutputStream(tmp);
		InputStream is = zipFile.getInputStream(zipEntry);
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.close();
		is.close();
	}

	private static void unzipDir(String filePath) {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static void forEach(File rootFile, OnEachListener lsnr) {
		if (rootFile == null || !rootFile.exists()) {
			return;
		}
		if (rootFile.isDirectory()) {
			for (File file : rootFile.listFiles()) {
				forEach(file, lsnr);
			}
		}

		lsnr.onEach(rootFile);
	}

	public interface OnEachListener {
		void onEach(File file);
	}

	private static OnEachListener mOnEachListener = new OnEachListener() {

		@Override
		public void onEach(File file) {
			try {
				if (file.isFile()) {
					unzipr(file);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public static void unzipr(File file) throws ZipException, IOException {
		if (!file.exists()) {
			return;
		}
		if (file.getAbsolutePath().endsWith(".zip")) {
			forEach(new File(unzip(file)), mOnEachListener);
		} else if (file.isDirectory()) {
			forEach(file, mOnEachListener);
		}
	}
}
