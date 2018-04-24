package com.anconet.lrp.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.impl.io.DefaultHttpRequestParser;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.impl.io.SessionInputBufferImpl;

import com.anconet.lrp.model.RadioStation;
import com.anconet.lrp.utils.Utils;

public class RequestHandler {

	private HttpTransportMetricsImpl metrics = new HttpTransportMetricsImpl();
	private SessionInputBufferImpl buffer = new SessionInputBufferImpl(metrics, 2048);
	private DefaultHttpRequestParser defaultHttpRequestParser = new DefaultHttpRequestParser(buffer);

	public RadioStation handleRequest(List<RadioStation> radioStations, Socket clientConnection) {

		RadioStation requestedRadioStation = null;

		try {
			buffer.bind(clientConnection.getInputStream());
			HttpRequest httpRequest = defaultHttpRequestParser.parse();

			if (!httpRequest.getRequestLine().getUri().equals("/")) {
				String requestedURI = Utils.removeFirstSlashFromHttpRequestUri(httpRequest).toLowerCase();

				for (RadioStation r : radioStations) {
					if (requestedURI.equals(r.getId())) {
						requestedRadioStation = r;
					}
				}
			}

		} catch (IOException | HttpException e) {
			e.printStackTrace();
		} finally {
			buffer.clear();
		}
		return requestedRadioStation;
	}
}