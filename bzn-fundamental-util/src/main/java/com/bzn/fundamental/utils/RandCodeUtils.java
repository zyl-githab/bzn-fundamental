package com.bzn.fundamental.utils;

/**
 * 随机码生成器
 * 
 * @author：fengli
 * @since：2016年9月2日 下午2:00:04
 * @version:
 */
public class RandCodeUtils {
	private final static String[] arr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

	/**
	 * 生产6位随机码
	 * 
	 * @return
	 */
	public static String getRandCode() {
		return getRandCode(6);
	}

	/**
	 * 生产length位随机码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandCode(int length) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append(arr[(int) (Math.random() * arr.length)]);
		}
		return sb.toString();

	}
}
