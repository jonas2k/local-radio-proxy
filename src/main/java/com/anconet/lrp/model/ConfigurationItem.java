package com.anconet.lrp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

public class ConfigurationItem {

	private int port;
	private Header defaultUserAgentHeader;
	private Header defaultRefererHeader;

	private boolean proxyMode;
	private String proxyHost;
	private int proxyPort;

	private List<RadioStation> radioStations = new ArrayList<RadioStation>();

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Header getDefaultUserAgentHeader() {
		return defaultUserAgentHeader;
	}

	public void setDefaultUserAgentHeader(Header defaultUserAgentHeader) {
		this.defaultUserAgentHeader = defaultUserAgentHeader;
	}

	public Header getDefaultRefererHeader() {
		return defaultRefererHeader;
	}

	public void setDefaultRefererHeader(Header defaultRefererHeader) {
		this.defaultRefererHeader = defaultRefererHeader;
	}

	public List<RadioStation> getRadioStations() {
		return radioStations;
	}

	public boolean isProxyMode() {
		return proxyMode;
	}

	public void setProxyMode(boolean proxyMode) {
		this.proxyMode = proxyMode;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void addRadioStation(RadioStation radioStation) {
		this.radioStations.add(radioStation);
	}
}