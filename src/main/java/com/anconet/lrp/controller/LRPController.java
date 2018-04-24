package com.anconet.lrp.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.anconet.lrp.model.ConfigurationItem;
import com.anconet.lrp.model.RadioStation;
import com.anconet.lrp.model.RadioStationNonExistentException;

public class LRPController {

	private static final Logger logger = Logger.getLogger(LRPController.class.getName());

	private final int port;
	private List<RadioStation> radioStations;

	private ExecutorService executorService = Executors.newCachedThreadPool();
	private PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private RequestHandler requestHandler = new RequestHandler();
	private ConfigurationItem settings;

	public LRPController(int port, List<RadioStation> radioStations, ConfigurationItem settings) {
		super();
		this.port = port;
		this.radioStations = radioStations;
		this.settings = settings;
	}

	public void control() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			Socket clientConnection = serverSocket.accept();

			System.out.println("Client connecting from " + clientConnection.getRemoteSocketAddress());

			RadioStation requestedRadioStation = requestHandler.handleRequest(radioStations, clientConnection);

			if (requestedRadioStation != null) {

				CloseableHttpClient httpClient;

				if (settings.isProxyMode()) {

					logger.log(Level.INFO,
							"Running in proxy mode using " + settings.getProxyHost() + ":" + settings.getProxyPort());

					HttpHost proxy = new HttpHost(settings.getProxyHost(), settings.getProxyPort());
					DefaultProxyRoutePlanner defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(proxy);

					httpClient = HttpClients.custom().setConnectionManager(cm).setRoutePlanner(defaultProxyRoutePlanner)
							.setConnectionManagerShared(true).build();
				} else {
					httpClient = HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).build();
				}

				executorService.execute(new Streamer(httpClient, requestedRadioStation, clientConnection));
			} else {
				clientConnection.close();
				throw new RadioStationNonExistentException("Requested radiostation doesn't exist.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RadioStationNonExistentException e) {
			e.printStackTrace();
		}
	}
}