package com.bzn.fundamental.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 描述: Base64工具类, 该工具类提供了将对象转换成Base64编码的方法和将Base64编码转换成对象的方法</br>
 * 
 * @author Shangxp
 * @version 1.0.0
 * @date 2016.03.23
 * 
 * Copyright © 2016 BZN Corporation, All Rights Reserved.
 */
@SuppressWarnings("restriction")
public class Base64Utils {

	public static String base64Encode(Serializable ser) throws IOException {
		// 该流与内存中的一个字节数组关联
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 将字节数组流与ObjectOutputStream流对接
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		// 将对象输出, 最终对象的字节"流"到了字节数组中
		oos.writeObject(ser);
		oos.flush();
		oos.close();
		// 从流中取出字节数组
		byte[] bytes = bos.toByteArray();
		bos.close();
		
		// String result = new String(bytes, "ISO-8859-1");
		
		// 将字节编码成Base64字符串
		BASE64Encoder encoder = new BASE64Encoder();
		String result = encoder.encode(bytes);
		
		// 默认生成了有换行和回车, 需要去掉, 否则在Servlet中读取的时候会产生问题
		result = result.replaceAll("\\r", "");
		result = result.replaceAll("\\n", "");
		
		return result;
	}
	
	public static Object base64Decode(String base64Str) throws IOException, ClassNotFoundException {
		// 将字符串还原成字节
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes = decoder.decodeBuffer(base64Str);
		// 使用字节数组输入流读取字节数组
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		// ByteArrayInputStream bis = new ByteArrayInputStream(base64Str.getBytes("ISO-8859-1"));
		
		// 与对象输入流对接
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object object = ois.readObject();
		ois.close();
		bis.close();
		
		return object;
	}
	
	/**
	 * 对URL中的参数进行UTF-8编码
	 */
	public static String urlEncodeUTF8(String parameter) throws UnsupportedEncodingException {
		parameter = parameter.replaceAll("\\+", "-");
		return URLEncoder.encode(parameter, "UTF-8");
	}
	
	/**
	 * 对URL中的参数进行UTF-8解码
	 */
	public static String urlDecodeUTF8(String parameter) throws UnsupportedEncodingException {
		return URLDecoder.decode(parameter, "UTF-8").replaceAll("\\-", "+");
	}
	
	/**
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Order order = new Order();
		order.setOrderCode("1500058040020077");
		order.setRemark("官网订单");
		
		// 输出编码后的结果
		String base64Str = base64Encode(order);
		System.out.println(base64Str);
		
		// 输出解码后的结果
		Order o = (Order) base64Decode(base64Str);
		System.out.println(o.getOrderCode() + "\t" + o.getRemark());
	}
	*/
}