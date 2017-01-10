package com.bzn.fundamental.excel.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author：fengli
 * @since：2015-12-21 下午1:03:43
 * @version:
 */
public class StringUtil extends StringUtils {

	/**
	 * 创建一个新的实例 StringUtil.
	 * 
	 */
	private StringUtil() {

	}

	/**
	 * 将String数组转换为制定分隔符的字符串
	 * 
	 * @param str 需要转换的Striing 数组
	 * @param split 转换后的分隔符
	 * @return 返回转换后的字符串
	 */
	public static String arrayToString(String[] str, String split) {
		StringBuffer sb = new StringBuffer();

		if (isArrayEmpty(str)) {
			return "";
		}

		String[] arrayOfString = str;
		int j = str.length;
		for (int i = 0; i < j; i++) {
			String string = arrayOfString[i];

			sb.append(string == null ? "" : string).append(split);
		}

		return sb.toString().substring(0, sb.toString().lastIndexOf(split));
	}

	/**
	 * 拼接sql添加 模糊查询的% （左右两边）
	 * 
	 * @param content 需要拼接的字段
	 * @return 返回拼接好的字符串
	 */
	public static String escapeAllLike(String content) {
		return escapeLike(content, 1);
	}

	/**
	 * 拼接sql添加 模糊查询的% （左边）
	 * 
	 * @param content 需要拼接的字段
	 * @return 返回拼接好的字符串
	 */
	public static String escapeLeftLike(String content) {
		return escapeLike(content, 2);
	}

	/**
	 * 拼接sql添加 模糊查询的% （右边）
	 * 
	 * @param content 需要拼接的字段
	 * @return 返回拼接好的字符串
	 */
	public static String escapeRightLike(String content) {
		return escapeLike(content, 3);
	}

	/**
	 * 根据type类型 拼接sql 添加 模糊查询的% （左右两边）
	 * 
	 * @param content 需要拼接的内容
	 * @param type 需要拼接%的类型 1 左右都需要拼接 ，2 左边拼接，3 右边拼接
	 * @return 返回拼接好的字符串
	 */
	private static String escapeLike(String content, int type) {
		if (isNotEmpty(content)) {
			content = content.replace("\\", "\\\\");
			content = content.replace("%", "\\%");
			content = content.replace("_", "\\_");

			switch (type) {
			case 1:
				return "%" + content + "%";
			case 2:
				return "%" + content;
			case 3:
				return content + "%";
			}

			return "%" + content + "%";
		}

		return content;
	}

	/**
	 * 判断字符串去除空格之后时都为null
	 * 
	 * @param str 需要判断的内容
	 * @return 是否为空 true 为null FALSE 不为null
	 */
	public static boolean isBlank(String str) {
		if ((str == null) || ("".equals(str.trim()))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为null
	 * 
	 * @param str 需要判断的内容
	 * @return 是否为空 true 为Empty FALSE 不为Empty
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	/**
	 * 判断字符串是否为notEmpty
	 * 
	 * @param str 需要判断的内容
	 * @return 是否为空 true 为Empty FALSE 不为Empty
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串是否为number类型
	 * 
	 * @param str 需要判断的字符串
	 * @return true 为数字类型 false不为数字类型
	 */
	public static boolean isNumber(String str) {
		return (isNotEmpty(str)) && (str.matches("^\\d+$"));
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

	/**
	 * 判断数组是否为空
	 * 
	 * @param array 需要判断的数组
	 * @return true为空 FALSE则不为空
	 */
	public static boolean isArrayEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	/**
	 * 判断字符串是否存在字符串数组中
	 * 
	 * @param strArray 字符串数组
	 * @param str 判断是否存在的字符串
	 * @return true 在数组中 FALSE 不在数组中
	 */
	public static boolean isStrInStrArray(String[] strArray, String str) {
		if ((isArrayEmpty(strArray)) || (isEmpty(str))) {
			return false;
		}

		String[] arrayOfString = strArray;
		int j = strArray.length;
		for (int i = 0; i < j; i++) {
			String tempStr = arrayOfString[i];

			if (str.equals(tempStr)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断数组中是否存在空白元素
	 * 
	 * @param strs 需要校验的字符串数组
	 * @return boolean true 存在空白 元素 FALSE 不存在 空白元素
	 */
	public static boolean isAnyElmentBlank(String[] strs) {
		if (strs == null) {
			return true;
		}

		if (strs.length > 0) {
			String[] arrayOfString = strs;
			int j = strs.length;
			for (int i = 0; i < j; i++) {
				String string = arrayOfString[i];

				if (isBlank(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 传递参数到前台特殊字符转义
	 * 
	 * @param str 需要处理特殊字符的字符串
	 * @return 返回处理后的字符串
	 */
	public static String htmlEncode(String str) {
		if ((str != null) && (!"".equals(str))) {
			str = str.replaceAll("\\\\", "\\\\\\\\").replaceAll("&", "&amp;")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
					.replaceAll("'", "&#39;");
		}
		return str;
	}

	/**
	 * 传递参数到前台特殊字符转义 \不转义
	 * 
	 * @param str 需要处理特殊字符的字符串
	 * @return String 返回处理后的字符串
	 */
	public static String htmlEncodeNoXg(String str) {
		if ((str != null) && (!"".equals(str))) {
			str = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
		}
		return str;
	}

	/**
	 * xml特殊字符转义
	 * 
	 * @param strData 需要转义的字符串
	 * @return String 返回转移之后的字符串
	 */
	public static String xmlencode(String strData) {
		if ((strData == null) || ("".equals(strData))) {
			return "";
		}

		strData = strData.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("'", "&apos;");
		return strData;
	}

	public static String unescapeHtml(String html) {
		if (StringUtils.isBlank(html))
			return html;
		return StringEscapeUtils.unescapeHtml3(StringUtils.trim(html));
	}

	/**
	 * xml特殊字符转义
	 * 
	 * @param strData 需要转义的字符串
	 * @return String 返回转移之后的字符串
	 */
	public static List<String> collByString(String str) {
		List<String> strColl = null;
		if (isNotEmpty(str)) {
			strColl = new ArrayList<String>();
			String[] strArr = str.split(",");
			for (String s : strArr) {
				strColl.add(s);
			}
		}
		return strColl;
	}

	/**
	 * 将字符串转换为数字list
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> intCollByString(String str) {
		List<Integer> intColl = null;
		if (isNotEmpty(str)) {
			intColl = new ArrayList<Integer>();
			String[] strArr = str.split(",");
			for (String s : strArr) {
				intColl.add(Integer.valueOf(s));
			}
		}
		return intColl;
	}

	public static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}

	/**
	 * 去掉空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null) {
			return str;
		} else {
			return str.trim();
		}
	}

	/**
	 * 去掉多余的0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

}
