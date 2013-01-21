package com.civilizedgravy.mlinstall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class zipit {

	List<String> fileList;
	private static final String OUTPUT_ZIP_FILE = null;
	private static final String SOURCE_FOLDER = null;

	public zipit() {
		fileList = new ArrayList<String>();
	}

	public void zipIt(String zipFile, String srcfolder) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to jar : " + zipFile);

			for (String file : this.fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(srcfolder
						+ File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList
	 * 
	 * @param node
	 *            file or directory
	 */
	public void generateFileList(File node, String srcfolder) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString(),
					srcfolder));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename), srcfolder);
			}
		}

	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String file, String srcfolder) {
		return file.substring(srcfolder.length(), file.length());
	}

	public final void copyDirectory(File source, File destination)
			throws IOException {
		if (!source.isDirectory()) {
			throw new IllegalArgumentException("Source (" + source.getPath()
					+ ") must be a directory.");
		}

		if (!source.exists()) {
			throw new IllegalArgumentException("Source directory ("
					+ source.getPath() + ") doesn't exist.");
		}

		

		//destination.mkdirs();
		File[] files = source.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				copyDirectory(file, new File(destination, file.getName()));
			} else {
				copyFile(file, new File(destination, file.getName()));
			}
		}
	}

	public void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				System.out.println("Directory is deleted : "
						+ file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	public static final void copyFile(File source, File destination)
			throws IOException {
		@SuppressWarnings("resource")
		FileChannel sourceChannel = new FileInputStream(source).getChannel();
		@SuppressWarnings("resource")
		FileChannel targetChannel = new FileOutputStream(destination)
				.getChannel();
		sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		sourceChannel.close();
		targetChannel.close();
	}
	void writeFile(JarOutputStream jos, File f) throws IOException {
		  byte[] buffer = new byte[1024];

		  if (f == null || !f.exists())
		      return; //

		  JarEntry jarAdd = new JarEntry(f.getName());
		  jarAdd.setTime(f.lastModified());
		  jos.putNextEntry(jarAdd);

		  if(f.isDirectory()){
		      File[] files = f.listFiles();
		      for(int i = 0; i < files.length ; i++){
		          writeFile(jos,files[i]);
		      }
		  }
		  else{
		      FileInputStream in = new FileInputStream(f);
		      while (true) {
		        int nRead = in.read(buffer, 0, buffer.length);
		        if (nRead <= 0)
		          break;
		        jos.write(buffer, 0, nRead);
		      }
		      in.close();
		  }
		}
}
