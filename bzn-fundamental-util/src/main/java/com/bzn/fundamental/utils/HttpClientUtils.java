package com.bzn.fundamental.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpclient工具类
 * 
 * @author：fengli
 * @since：2016年8月16日 下午4:06:20
 * @version:
 */
public class HttpClientUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
	private PoolingHttpClientConnectionManager poolConnManager;
	private final int maxTotalPool = 200;
	private final int maxConPerRoute = 20;
	private final int socketTimeout = 20000;
	private final int connectionRequestTimeout = 30000;
	private final int connectTimeout = 10000;

	public void init() {

		SSLContext sslcontext = SSLContexts.createSystemDefault();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();

		poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolConnManager.setMaxTotal(maxTotalPool);
		poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
		poolConnManager.setDefaultSocketConfig(socketConfig);
	}

	public CloseableHttpClient getConnection() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
				.setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}

	public static HttpClientUtils getHttpClient() {
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		return httpClientUtils;
	}

	public String postMsg(String url, Map<String, String> params) {
		String responseContent = null;
		
		HttpPost httpPost = new HttpPost(url);
		try {
			if (params == null) {
				return null;
			}
			List<NameValuePair> nvps = new ArrayList<>();
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			CloseableHttpClient httpClient = this.getConnection();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			responseContent = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception", e.getMessage());
		} finally {
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	public CloseableHttpResponse post(String url, Map<String, String> params) {
		CloseableHttpResponse response = null;

		HttpPost httpPost = new HttpPost(url);
		try {
			if (params == null) {
				return null;
			}
			List<NameValuePair> nvps = new ArrayList<>();
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			CloseableHttpClient httpClient = this.getConnection();
			response = httpClient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception", e.getMessage());
		} finally {
			httpPost.releaseConnection();
		}
		return response;
	}
	
	/**
	 * POST
	 * @param reqURL
	 * @param params json字符串
	 * @return
	 */
	public String postJson(String reqURL, String params) {
		String responseContent = null;
		HttpPost httpPost = new HttpPost(reqURL);
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout)
					.setConnectionRequestTimeout(connectTimeout).build();

			StringEntity se = new StringEntity(params, ContentType.APPLICATION_JSON);
			httpPost.setEntity(se);
			httpPost.setConfig(requestConfig);
			CloseableHttpClient httpClient = this.getConnection();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				// 执行POST请求
				HttpEntity entity = response.getEntity(); // 获取响应实体
				try {
					if (null != entity) {
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
			LOGGER.info("requestURI : " + httpPost.getURI());
			LOGGER.info(", responseContent: " + responseContent);
		} catch (ClientProtocolException e) {
			LOGGER.error("ClientProtocolException", e);
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		} finally {
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	/**
	 * http Post服务
	 * 
	 * @param strJson
	 * @return 返回数据
	 */
	public String post(String url, String strJson) {
		String rspText = null;
		BufferedReader in = null;
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpResponse response =null;
		HttpRequestBase httpMethod = new HttpPost(url);
		try {

			StringEntity entity = new StringEntity(String.valueOf(strJson), "UTF-8");
			entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			((HttpPost) httpMethod).setEntity(entity);
			int statusCode = 0;
			CloseableHttpClient httpClient = this.getConnection();
			response = httpClient.execute(httpMethod);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();
				in = new BufferedReader(new InputStreamReader(resEntity.getContent(), "UTF-8"));
				while ((rspText = in.readLine()) != null) {
					stringBuffer.append(rspText);
				}
				rspText = stringBuffer.toString();
			}
			LOGGER.info(String.format("post响应信息  -> %s \t %s", statusCode, rspText));
		} catch (Exception e) {
			LOGGER.error("发送数据 error, url:",e);
		} finally {

			httpMethod.releaseConnection();
			try {
				response.close();
			} catch (IOException e) {
				LOGGER.error("rsponse.close error:",e);
			}
		}
		return rspText;
	}
	
	public String get(String url, Map<String, String> params) {
		String responseString = null;
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			if (i == 0 && !url.contains("?")) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(entry.getKey());
			sb.append("=");
			String value = entry.getValue();
			try {
				sb.append(URLEncoder.encode(value, "utf8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.warn("encode http get params error, value is " + value, e);
				// sb.append(URLEncoder.encode(value));
			}
			i++;
		}
		HttpGet get = new HttpGet(sb.toString());
		try {
			CloseableHttpResponse response = this.getConnection().execute(get);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, "utf8");
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				LOGGER.error(
						String.format("[HttpUtils Get]get response error, url:%s", sb.toString()),
						e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			LOGGER.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:",
					sb.toString(), responseString));
		} catch (SocketTimeoutException e) {
			LOGGER.error(
					String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()),
					e);
			return responseString;
		} catch (Exception e) {
			LOGGER.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()),
					e);
		} finally {
			// get.releaseConnection();
		}
		return responseString;
	}
}
