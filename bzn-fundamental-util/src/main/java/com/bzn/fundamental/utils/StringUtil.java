package com.bzn.fundamental.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

	public static Boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str) || "null".equals(str)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 将对象转位字符串 null的时候转为""
	 * 
	 * @param objStr 需要转换的对象
	 * @return String 转后的字符串
	 */
	public static String parseNull(Object objStr) {
		return objStr == null ? "" : objStr.toString();
	}

	private static int compare(String str, String target) {
		int d[][];
		int n = str.length();
		int m = target.length();
		int i;
		int j;
		char ch1;
		char ch2;
		int temp;
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) {
			ch1 = str.charAt(i - 1);
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}

				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}

	private static int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	/**
	 * 比较字符串相似度
	 * 
	 * @param str
	 * @param target
	 * @return
	 */
	public static float getSimilarityRatio(String source, String target) {
		return 1 - (float) compare(source, target) / Math.max(source.length(), target.length());
	}
}
