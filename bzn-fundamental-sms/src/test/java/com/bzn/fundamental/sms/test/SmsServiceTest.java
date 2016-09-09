package com.bzn.fundamental.sms.test;

import java.util.HashMap;

import com.bzn.fundamental.sms.SmsService;
import com.bzn.fundamental.sms.SmsTemplate;

public class SmsServiceTest {

	public static void main(String[] args) {

		String telNumbers = "13141383186";
		// String templateText =
		// SmsTemplate.TEMPLATE_TEXT_REGISTER_NOTICE_COMPANY;
		String[] data = new String[] { "zhangsan", "12346" };
		// String[] datas = new String[] {
		// SmsTemplate.macroSubstitution(templateText, data) };
		HashMap<String, Object> map = SmsService
				.asyncSendSms(SmsTemplate.TEMPLATE_ID_REGISTER_NOTICE_COMPANY, telNumbers, data);

		System.out.println(map);
	}
}
