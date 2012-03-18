package org.jabox.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
	public static void unzip(File zipFile, File baseDir) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry e;

		while ((e = zin.getNextEntry()) != null) {
			if (!e.isDirectory()) {
				unzip(zin, baseDir, e.getName());
			}
		}
		zin.close();
	}

	public static void unzip(ZipInputStream zin, File baseDir, String entry)
			throws IOException {
		File file = new File(baseDir, entry);
		System.out.println("unzipping " + file.getAbsolutePath());
		file.getParentFile().mkdirs();
		file.setExecutable(true);
		FileOutputStream out = new FileOutputStream(file);
		byte[] b = new byte[512];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
	}
}
