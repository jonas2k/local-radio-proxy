package com.anconet.lrp.utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpRequest;

public class Utils {

	public static boolean isReallyHeadless() {
		if (GraphicsEnvironment.isHeadless()) {
			return true;
		}
		try {
			GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			return screenDevices == null || screenDevices.length == 0;
		} catch (HeadlessException e) {
			e.printStackTrace();
			return true;
		}
	}

	public static void writeHeader(String header, OutputStream outputStream) {

		try {
			outputStream.write((header + "\r\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeHeader(Header header, OutputStream outputStream) {

		try {
			outputStream.write((header.toString() + "\r\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String removeFirstSlashFromHttpRequestUri(HttpRequest httpRequest) {
		return httpRequest.getRequestLine().getUri().replaceFirst("/", "");
	}
}