package com.bzn.fundamental.utils;

import java.io.IOException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * httpclient工具类
 * 
 * @author：fengli
 * @since：2016年8月16日 下午4:06:20
 * @version:
 */
public class HttpClientUtils {

	private final static Logger LOGGER = LogManager.getLogger(HttpClientUtils.class);
	private PoolingHttpClientConnectionManager poolConnManager;
	private final int maxTotalPool = 200;
	private final int maxConPerRoute = 20;
	private final int socketTimeout = 2000;
	private final int connectionRequestTimeout = 3000;
	private final int connectTimeout = 1000;

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

	public String postMsg(String url, Map<String, String> params) {
		String responseContent = null;

		CloseableHttpResponse response = post(url, params);
		try {
			// 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, Consts.UTF_8);
				entity.getContent().close();
			}

		} catch (Exception e) {
			LOGGER.error("Exception", e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOGGER.error("Exception", e.getMessage());
				}
			}
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
			HttpEntity entity = new StringEntity(JSON.toJSONString(params), ContentType
					.create(ContentType.APPLICATION_JSON.getMimeType(), CharEncoding.UTF_8));
			httpPost.setEntity(entity);
			CloseableHttpClient httpClient = this.getConnection();
			response = httpClient.execute(httpPost);
		} catch (Exception e) {
			LOGGER.error("Exception", e.getMessage());
		} finally {
			httpPost.releaseConnection();
		}
		return response;
	}
}
