package com.anconet.lrp.model;

import java.net.URL;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class RadioStation {

	private String name = "";
	private String id = "";
	private URL url = null;
	private boolean isIcy = false;
	private Header userAgent = null;
	private Header referer = null;

	public RadioStation(String name, URL url, boolean isIcy, Header userAgent, Header referer) {
		super();
		this.name = name;
		this.id = generateIdFrom(name);
		this.url = url;
		this.isIcy = isIcy;
		this.userAgent = userAgent;
		this.referer = referer;
	}

	public RadioStation(String name, URL url, boolean isIcy, String userAgent, String referer) {
		this(name, url, isIcy, new BasicHeader("User-Agent", userAgent), new BasicHeader("Referer", referer));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getUrlAsString() {
		return url.toString();
	}

	public boolean isIcy() {
		return isIcy;
	}

	public void setIcy(boolean isIcy) {
		this.isIcy = isIcy;
	}

	public Header getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(Header userAgent) {
		this.userAgent = userAgent;
	}

	public Header getReferer() {
		return referer;
	}

	public void setReferer(Header referer) {
		this.referer = referer;
	}

	private String generateIdFrom(String input) {
		return input.trim().toLowerCase().replaceAll("\\s|\\.", "");
	}
}