package com.bzn.fundamental.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author：fengli
 * @since：2016年8月16日 下午5:55:11
 * @version:
 */
public class MD5Utils {

	/**
	 * 创建一个新的实例 MD5Util.
	 * 
	 */
	private MD5Utils() {

	}

	/**
	 * md5加密方法 默认UTF-8编码
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String getMD5Str(String sourceStr) {

		return getMD5Str(sourceStr, "UTF-8");
	}

	/**
	 * md5加密方法
	 * 
	 * @param sourceStr 需要加密的信息
	 * @param charSet 试用的编码格式
	 * @return
	 */
	public static String getMD5Str(String sourceStr, String charSet) {

		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			try {

				md.update(sourceStr.getBytes(charSet));

			} catch (UnsupportedEncodingException e) {

				md.update(sourceStr.getBytes());

			}
			byte b[] = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

}
