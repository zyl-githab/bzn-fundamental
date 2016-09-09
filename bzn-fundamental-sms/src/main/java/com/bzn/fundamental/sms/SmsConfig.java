package com.bzn.fundamental.sms;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * sms 配置信息
 * 
 * @author：fengli
 * @since：2016年8月24日 上午11:12:50
 * @version:
 */
public class SmsConfig {

	/**
	 * 日志工具类
	 */
	private static Logger LOGGER = LogManager.getLogger(SmsConfig.class);

	/**
	 * 验证账户id
	 */
	public static String CCP_ACCOUNTS_ID = "aaf98f895147cd2a01516b05b46855da";

	/**
	 * 验证账户token
	 */
	public static String CCP_ACCOUNT_TOKEN = "4190680f7e46494c8bcf6920887e914f";

	/**
	 * appId，在引用模版里面查找
	 */
	public static String CCP_APP_ID = "8a48b5515350d1e20153750c0b923c53";

	/**
	 * serverIp地址
	 */
	public static String CCP_SERVER_IP = "app.cloopen.com";

	/**
	 * server端口
	 */
	public static String CCP_SERVER_PORT = "8443";

	/**
	 * 短信发送开关
	 */
	public static String CCP_SEND_SMS_SWITCH = "1";

	/**
	 * 短信开关-打开
	 */
	public static String CCP_SEND_SMS_OPEN = "1";

	/**
	 * 短信开关-关闭
	 */
	public static String CCP_SEND_SMS_CLOSE = "0";

	static {
		try {
			Properties properties = PropertiesLoaderUtils
					.loadProperties(new ClassPathResource("sms.properties"));

			SmsConfig.CCP_ACCOUNTS_ID = properties.getProperty("ccp.accounts.id");
			SmsConfig.CCP_ACCOUNT_TOKEN = properties.getProperty("ccp.account.token");
			SmsConfig.CCP_APP_ID = properties.getProperty("ccp.app.id");
			SmsConfig.CCP_SERVER_IP = properties.getProperty("ccp.server.ip");
			SmsConfig.CCP_SERVER_PORT = properties.getProperty("ccp.server.port");
			SmsConfig.CCP_SEND_SMS_SWITCH = properties.getProperty("ccp.send.sms.switch");
		} catch (IOException ex) {
			LOGGER.error("load sms config file error:" + ex.getMessage());
		}
	}
}
