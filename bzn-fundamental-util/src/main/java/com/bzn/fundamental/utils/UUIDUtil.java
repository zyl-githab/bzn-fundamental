package com.bzn.fundamental.utils;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author：fengli
 * @since：2016年9月21日 下午1:00:43
 * @version:
 */
public class UUIDUtil {

	/**
	 * 生成32为uuid字符串
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
