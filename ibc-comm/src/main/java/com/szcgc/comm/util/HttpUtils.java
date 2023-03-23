package com.szcgc.comm.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HttpUtil.java
 * @Description: TODO
 * @author liaohong
 * @date May 16, 2022 2:59:08 PM
 * @version V1.0
 */
public class HttpUtils {

	/*
	 * fluent-hc风格的，comm不需要，所有屏蔽,就可以不需要引起对应的jar
	 * 
	 * public final static Content quickGet(String uri) throws Exception { //
	 * Request.Get("http://somehost/").connectTimeout(1000).socketTimeout(1000).
	 * execute().returnContent().asString(); return
	 * Request.Get(uri).execute().returnContent(); }
	 * 
	 * public final static Content quickPost(String uri) throws Exception { //
	 * Request.Post("http://targethost/login").bodyForm(Form.form().add("username",
	 * // "vip").add("password", "secret").build()).execute().returnContent();
	 * return Request.Post(uri).execute().returnContent(); }
	 * 
	 * public final static String quickGetString(String uri) throws Exception {
	 * return quickGet(uri).asString(Consts.UTF_8); }
	 */

	public final static String httpGet(String uri) throws Exception {
		return httpGet(uri, null);
	}

	public final static String httpGet(String uri, Map<String, String> head) throws Exception {
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
				} else {
					throw new ClientProtocolException("Unexpected get response status: " + status + "--" + uri);
				}
			}

		};
		Header[] headers = wrapHeader(head);
		return httpGet(uri, headers, responseHandler);
	}

	public final static <T> T httpGet(final String uri, final Header[] headers, final ResponseHandler<? extends T> responseHandler) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
			HttpGet httpget = new HttpGet(uri);
			httpget.setConfig(reqConfig);
			if (headers != null && headers.length > 0)
				httpget.setHeaders(headers);
			return httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.close();
		}
	}

	// private static final Header[] DFT_HEADER = new Header[] {
	// new BasicHeader("Content-Type", "text/xml; charset=utf-8") };

	private static HttpEntity wrapEntity(String body) {
		if (body == null || body.length() <= 0)
			return null;
//		StringEntity entity= new StringEntity(body, Consts.UTF_8);
//		entity.setContentEncoding("utf-8");
//		return entity;
		return new StringEntity(body, Consts.UTF_8);
	}

	private static HttpEntity wrapEntity(byte[] bytes) {
		if (bytes == null)
			return null;
		return new ByteArrayEntity(bytes);
	}

	private static HttpEntity wrapEntity(Map<String, String> body) {
		if (body == null || body.isEmpty())
			return null;
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : body.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return new UrlEncodedFormEntity(pairs, Consts.UTF_8);
	}

	private static Header[] wrapHeader(Map<String, String> head) {
		if (head == null || head.isEmpty())
			return null;
		Header[] headers = new Header[head.size()];
		int index = 0;
		for (Map.Entry<String, String> entry : head.entrySet()) {
			headers[index++] = new BasicHeader(entry.getKey(), entry.getValue());
		}
		return headers;
	}

	public final static String httpPost(String uri, String body) throws Exception {
		return httpPost(uri, null, wrapEntity(body));
	}

	public final static String httpPost(String uri, Map<String, String> body) throws Exception {
		return httpPost(uri, null, wrapEntity(body));
	}

	public final static String httpPost(String uri, String body, Map<String, String> head) throws Exception {
		return httpPost(uri, wrapHeader(head), wrapEntity(body));
	}
	
	public final static String httpPost(String uri, byte[] body, Map<String, String> head) throws Exception {
		return httpPost(uri, wrapHeader(head), wrapEntity(body));
	}

	public final static String httpPost(final String uri, final Header[] headers, final HttpEntity entity) throws Exception {
		ResponseHandler<String> handler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
				} else {
					HttpEntity entity = response.getEntity();
					if(entity != null) {
						System.out.println(EntityUtils.toString(entity, Consts.UTF_8));
					}
					throw new ClientProtocolException("Unexpected post response status: " + status + "--" + uri);
				}
			}

		};
		return httpPost(uri, headers, entity, handler);
	}

	public final static <T> T httpPost(final String uri, final Header[] headers, final HttpEntity entity, final ResponseHandler<? extends T> handler) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
			HttpPost httpost = new HttpPost(uri);
			httpost.setConfig(reqConfig);
			if (headers != null && headers.length > 0)
				httpost.setHeaders(headers);
			if (entity != null)
				httpost.setEntity(entity);
			return httpclient.execute(httpost, handler);
		} finally {
			httpclient.close();
		}
	}

	public final static String httpPut(String uri, byte[] bytes, HashMap<String, String> head) throws Exception {

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				return String.valueOf(status);
			}
		};

		HttpEntity entity = wrapEntity(bytes);
		Header[] headers = wrapHeader(head);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// RequestTimeout（连接池获取到连接的超时时间）、
			// ConnectTimeout（建立连接的超时）、
			// SocketTimeout（获取数据的超时时间）
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(5000).setSocketTimeout(10000).build();
			HttpPut httput = new HttpPut(uri);
			httput.setConfig(reqConfig);
			if (headers != null && headers.length > 0)
				httput.setHeaders(headers);
			if (entity != null)
				httput.setEntity(entity);
			return httpclient.execute(httput, responseHandler);
		} finally {
			httpclient.close();
		}
	}

	public final static String httpDelete(final String uri) throws Exception {
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
				} else {
					throw new ClientProtocolException("Unexpected get response status: " + status + "--" + uri);
				}
			}
		};

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
			HttpDelete httpdel = new HttpDelete(uri);
			httpdel.setConfig(reqConfig);
			return httpclient.execute(httpdel, responseHandler);
		} finally {
			httpclient.close();
		}
	}
}
