package com.bzn.fundamental.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

	public static Boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str) || "null".equals(str)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
