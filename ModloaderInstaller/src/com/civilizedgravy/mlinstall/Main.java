package com.civilizedgravy.mlinstall;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.jar.JarOutputStream;

public class Main {

	public Main() throws Exception {

		String mcpath = null;
		if (OSValidator.isWindows()) {
			mcpath = "%appdata%/.minecraft/bin/minecraft.jar";
		} else if (OSValidator.isMac()) {
			mcpath = "/Library/Application Support/Minecraft/bin/minecraft.jar";
		} else if (OSValidator.isUnix()) {
			mcpath = System.getProperty("user.home")+"/.minecraft/bin/minecraft.jar";
		}

		byte[] buffer = new byte[1024];
		ExtractJar ej = new ExtractJar();

		String path = Main.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		System.out.println(decodedPath);
		File temp = new File(decodedPath + "/temp/minecraft/");
		File tempml = new File(decodedPath + "/temp"  + "/modloader/");
		tempml.mkdir();
		temp.mkdir();
		URL url; // represents the location of the file we want to dl.
		URLConnection con; // represents a connection to the url we want to dl.
		DataInputStream dis; // input stream that will read data from the file.
		FileOutputStream fos; // used to write data from inut stream to file.
		byte[] fileData; // byte aray used to hold data from downloaded file.
		try {
			url = new URL(
					"http://dl.dropbox.com/u/20629262/Latest/ModLoader.zip");
			con = url.openConnection(); // open the url connection.
			dis = new DataInputStream(con.getInputStream()); // get a data
																// stream from
																// the url
																// connection.
			fileData = new byte[con.getContentLength()]; // determine how many
															// byes the file
															// size is and make
															// array big enough
			System.out.println("Downloading Modloader!"); // to hold the data
			for (int x = 0; x < fileData.length; x++) { // fill byte array with

				fileData[x] = dis.readByte();
			}

			dis.close(); // close the data input stream
			fos = new FileOutputStream(new File("ModLoader.zip")); // create
																					// an
																					// object
																					// representing
																					// the
																					// file
																					// we
																					// want
																					// to
																					// save
			fos.write(fileData); // write out the file we want to save.
			fos.close(); // close the output stream writer
		} catch (MalformedURLException m) {
			System.out.println(m);
		} catch (IOException io) {
			System.out.println(io);
		}
		zipit z = new zipit();

		ej.extractJar(new File(decodedPath + "ModLoader.zip"), tempml);
		ej.extractJar(new File(mcpath), temp);
		z.copyDirectory(tempml, temp);
		z.delete(new File(temp + "/META-INF/"));
		JarOutputStream jos;
		
			if (mcpath != null) {
				
				
				z.generateFileList(temp , temp.toString());
				z.zipIt("minecraft.jar", temp.toString());
				
			}else{
				
			}
			
		

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		new Main();

	}

}
