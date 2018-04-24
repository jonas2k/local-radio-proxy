package com.anconet.lrp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.anconet.lrp.model.RadioStation;
import com.anconet.lrp.model.RequestDataHolder;
import com.anconet.lrp.utils.RequestUtils;
import com.anconet.lrp.utils.Utils;

public class Streamer implements Runnable {

	private static final Logger logger = Logger.getLogger(Streamer.class.getName());
	
	private final CloseableHttpClient closeableHttpClient;
	private RadioStation radioStation;
	private RequestDataHolder requestDataHolder;
	private Socket clientConnection;
	
	private final HttpContext httpContext;
	private OutputStream outputStream;
	private List<Header> responseHeaders;
	
	public Streamer(CloseableHttpClient closeableHttpClient, RadioStation radioStation, Socket clientConnection) {
		super();
		this.closeableHttpClient = closeableHttpClient;
		this.radioStation = radioStation;
		this.clientConnection = clientConnection;
		this.httpContext = HttpClientContext.create();
	}

	@Override
	public void run() {
		
		try {
			requestDataHolder = RequestUtils.requestRadioStation(closeableHttpClient, radioStation, httpContext);
			responseHeaders = requestDataHolder.getItemFromMap("responseHeaders");
			outputStream = clientConnection.getOutputStream();
			
			Utils.writeHeader("HTTP/1.1 200 OK", outputStream);

			responseHeaders.forEach(header -> Utils.writeHeader(header, outputStream));
			
			Utils.writeHeader("", outputStream);

			requestDataHolder.getHttpEntity().writeTo(outputStream);
			
		} catch (IOException e) {
			if (e instanceof SocketException) {
				logger.log(Level.INFO, "Client connection terminated");
			} else {
				e.printStackTrace();
			} 
		} finally {
			try {
				closeableHttpClient.close();
				clientConnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}