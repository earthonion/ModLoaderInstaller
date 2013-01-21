package com.civilizedgravy.mlinstall;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtractZip {

	public class UnzipFile {

		private void doUnzipFiles(String zipFileName) {

		try {

		ZipFile zf = new ZipFile(zipFileName);

		System.out.println("Archive: " + zipFileName);

		// Enumerate each entry
		for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {

		// Get the entry and its name
		ZipEntry zipEntry = (ZipEntry)entries.nextElement();
		String zipEntryName = zipEntry.getName();
		System.out.println(" inflating: " + zipEntryName);

		OutputStream out = new FileOutputStream(zipEntryName);
		InputStream in = zf.getInputStream(zipEntry);

		byte[] buf = new byte[1024];
		int len;
		while((len = in.read(buf)) > 0) {
		out.write(buf, 0, len);
		}

		// Close streams
		out.close();
		in.close();
		}

		} catch (IOException e) {
		e.printStackTrace();
		System.exit(1);
		}

		}

}}
