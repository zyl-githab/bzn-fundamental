package com.bzn.fundamental.sms;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * 短信发送服务类
 * 
 * @author：fengli
 * @since：2016年8月24日 下午1:20:44
 * @version:
 */
public class SmsService {

	private static Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

	private static ExecutorService executorService = Executors.newScheduledThreadPool(10,
			Executors.defaultThreadFactory());

	/**
	 * 异步发送短信
	 * 
	 * @param templateId 模版id
	 * @param telNumbers 手机号码
	 * @param datas 发送内容
	 * @return
	 */
	public static HashMap<String, Object> asyncSendSms(final String templateId,
			final String telNumbers, final String[] datas) {

		Future<HashMap<String, Object>> resultMap = executorService
				.submit(new Callable<HashMap<String, Object>>() {
					@Override
					public HashMap<String, Object> call() throws Exception {
						return sendSms(templateId, telNumbers, datas);
					}

				});

		try {
			return resultMap.get();
		} catch (InterruptedException e) {
			LOGGER.error("获取短信发送内容失败", e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.error("获取短信发送内容失败", e.getMessage());
		}
		return null;
	}

	/**
	 * 发送短信
	 * 
	 * @param templateId
	 * @param telNumbers
	 * @param datas
	 * @return
	 */
	public static HashMap<String, Object> sendSms(final String templateId, final String telNumbers,
			final String[] datas) {
		if (SmsConfig.CCP_SEND_SMS_SWITCH.equals(SmsConfig.CCP_SEND_SMS_CLOSE)) {
			LOGGER.info("开关未打开，发送数据为：{telNumbers:" + telNumbers + ",datas"
					+ ArrayUtils.toString(datas) + "}");
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("statusCode", "99");
			resultMap.put("statusMsg", "开关未打开");
			return resultMap;
		}

		LOGGER.info("{telNumbers:" + telNumbers + ",datas" + ArrayUtils.toString(datas) + "}");

		CCPRestSDK restApi = new CCPRestSDK();
		restApi.init(SmsConfig.CCP_SERVER_IP, SmsConfig.CCP_SERVER_PORT);
		restApi.setAccount(SmsConfig.CCP_ACCOUNTS_ID, SmsConfig.CCP_ACCOUNT_TOKEN);
		restApi.setAppId(SmsConfig.CCP_APP_ID);
		return restApi.sendTemplateSMS(telNumbers, templateId, datas);
	}
}
