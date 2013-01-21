package com.civilizedgravy.mlinstall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtractJar {

	public void extractJar(File jarFile, File destDir) throws Exception {
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> entries = jar.entries();
		
		while (entries.hasMoreElements()) {
			JarEntry file = (JarEntry) entries.nextElement();
			File f = new File(destDir + File.separator + file.getName());
			InputStream inputStream = jar.getInputStream(file);
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				f.getParentFile().mkdirs();
				fileOutputStream = new FileOutputStream(f);
			}
			byte[] b = new byte[16384];
			int bytes;
			while ((bytes = inputStream.read(b)) > 0) {
				fileOutputStream.write(b, 0, bytes);
			}
			
			fileOutputStream.close();
			inputStream.close();
		}
	}
}
