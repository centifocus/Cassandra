package wangliqiu.net;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import org.apache.log4j.Logger;

import java.io.*;

public class DownLoad {
	public static void main(String[] args) {

		String str = "";
		String fileStr = "";
		try {
			URL url = new URL(str);
			url.getHost();
			url.getPort();
			/*
			 * URLConnection instance does not establish the actual network connection . only when
			 * calling URLConnection.connect().
			 */
			URLConnection urlCon = url.openConnection();
			urlCon.connect();

			urlCon.getContentType();
			urlCon.getContentLength();

			final InputStream in = urlCon.getInputStream();
			urlCon.setReadTimeout(1000);
			final FileOutputStream out = new FileOutputStream(fileStr);

			int byteRead;
			while ((byteRead = in.read()) != -1) {
				out.write(byteRead);
			}
			in.close();
			out.close();

		} catch (Exception ex) {

		}
	}
}
