package com.anconet.lrp.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.anconet.lrp.model.RadioStation;
import com.anconet.lrp.model.RequestDataHolder;

public class RequestUtils {

	public static RequestDataHolder requestRadioStation(CloseableHttpClient httpClient, RadioStation radioStation,
			HttpContext httpContext) throws ClientProtocolException, IOException {

		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(radioStation.getUrlAsString())
				.setHeader(radioStation.getUserAgent()).setHeader(radioStation.getReferer()).build();

		if (radioStation.isIcy()) {
			httpUriRequest.addHeader("Icy-MetaData", "1");
		}

		List<Header> requestHeaders = Arrays.asList(httpUriRequest.getAllHeaders());

		requestHeaders.forEach(header -> System.out.println(header));

		CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest, httpContext);
		List<Header> responseHeaders = Arrays.asList(httpResponse.getAllHeaders());
		HttpEntity entity = httpResponse.getEntity();

		return new RequestDataHolder(entity, requestHeaders, responseHeaders);
	}
}