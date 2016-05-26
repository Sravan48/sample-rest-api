package com.cognitivescale.rest.util;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestUtil {

	private static final Logger logger = Logger.getLogger(RestUtil.class.toString());


	public static HttpClientBuilder getHttpClientBuilder(CredentialsProvider cp) {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		if(cp != null) {
			httpClientBuilder.setDefaultCredentialsProvider(cp);
		}
		return httpClientBuilder; 
	}

	public static void addHeaders(HttpRequest http, Map<String, String> headers) {
		if(headers != null) {
			for(String key : headers.keySet()) {
				http.setHeader(key, headers.get(key));
			}
		}
	}

	public static CloseableHttpResponse executeHttpRequest(CloseableHttpClient httpClient, HttpUriRequest httpUriReq, Map<String, String> headers) throws ClientProtocolException, IOException {
		addHeaders(httpUriReq, headers);
		logger.log(Level.FINE, "Executing request " + httpUriReq.getRequestLine());
		return httpClient.execute(httpUriReq);
	}

	public static String extractResponse(CloseableHttpResponse httpResp) throws ParseException, IOException {
		try {
			logger.log(Level.FINE, httpResp.getStatusLine().toString());
			return EntityUtils.toString(httpResp.getEntity());
		} finally {
			if(httpResp != null) {
				httpResp.close();
			}
		}
	}

	public static String execute(CloseableHttpClient httpClient, HttpEntityEnclosingRequestBase httpUriReq, Map<String, String> headers, String payload) throws ClientProtocolException, IOException {
		try {
			httpUriReq.setEntity(new ByteArrayEntity(payload.getBytes()));
			return EntityUtils.toString(executeHttpRequest(httpClient, httpUriReq, headers).getEntity());
		} finally {
			if(httpClient != null) {
				httpClient.close();
			}
		}
	}
	
	public static String execute(CloseableHttpClient httpClient, HttpUriRequest httpUriReq, Map<String, String> headers) throws ClientProtocolException, IOException {
		try {
			return EntityUtils.toString(executeHttpRequest(httpClient, httpUriReq, headers).getEntity());
		} finally {
			if(httpClient != null) {
				httpClient.close();
			}
		}
	}

	public static String GET(String url, Map<String, String> headers) throws HttpException, IOException {
		return execute(HttpClients.custom().build(), new HttpGet(url), headers);

	}

	public static String POST(String url, Map<String, String> headers, String payload) throws HttpException, IOException {
		return execute(HttpClients.custom().build(), new HttpPost(url), headers, payload);
	}

	public static String PUT(String url, Map<String, String> headers, String payload) throws HttpException, IOException {
		return execute(HttpClients.custom().build(), new HttpPut(url), headers, payload);
	}

	public static String Patch(String url, Map<String, String> headers, String payload) throws HttpException, IOException {
		return execute(HttpClients.custom().build(), new HttpPatch(url), headers, payload);
	}

	public static void Delete(String url, Map<String, String> headers, String payload) throws HttpException, IOException {
		executeHttpRequest(HttpClients.custom().build(), new HttpDelete(url), headers);
	}

	public static void main(String args[]) throws HttpException, IOException {
		System.out.println(GET("http://localhost:8080/sample-rest-api/test", null));
	}
}
