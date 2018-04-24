package com.anconet.lrp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class RequestDataHolder {
	
	private HttpEntity httpEntity;
	private Map<String, List<Header>> headerMap = new HashMap<String, List<Header>>();
	
	public RequestDataHolder() {
	}
	
	public RequestDataHolder(HttpEntity httpEntity, List<Header> requestHeaders, List<Header> responseHeaders) {
		super();
		this.httpEntity = httpEntity;
		this.addItemToMap("requestHeaders", requestHeaders);
		this.addItemToMap("responseHeaders", responseHeaders);
	}

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
	
	public void addItemToMap(String identifier, List<Header> headerList) {
		this.headerMap.put(identifier, headerList);
	}
	
	public List<Header> getItemFromMap(String identifier) {
		return this.headerMap.get(identifier);
	}
}