package com.bzn.fundamental.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * 描述: 日期, 数字以及货币等通用格式化工具类<br>
 * 
 * @author Shangxp
 * @version 1.0.0
 * @date 2016.03.22
 * 
 * Copyright © 2016 BZN Corporation, All Rights Reserved.
 */
public class FormatUtils {
	
	private static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 将Date类型转换为String类型, 并格式化
	 */
	public static String formatDate2String(Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString("yyyy-MM-dd");
	}
	
	/**
	 * 将Date类型转换为String类型, 并格式化
	 */
	public static String formatDate2String(Date date, String pattern) {
		DateTime dt = new DateTime(date);
		if (StringUtils.isNotBlank(pattern)) {
			return dt.toString(pattern);
		} else {
			pattern = "yyyy年MM月dd日 HH:mm:ss";
			return dt.toString(pattern, Locale.CHINESE);
		}
	}

	/**
	 * BigDecimal转String, 保留2位小数
	 */
	public static String formatL2BigDecimal(BigDecimal v) {
		if (v == null) {
			v = new BigDecimal("0.00");
		}
		
		v = v.setScale(2, BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat("#.00");
		
		return df.format(v.doubleValue());
	}
	
	/**
	 * BigDecimal四舍五入, 并设置精度
	 */
	public static BigDecimal formatBigDecimalRound(BigDecimal v, int scale) {
		if (v == null) {
			v = new BigDecimal("0.0000").setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else {
			v = v.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return v;
	}
	
	/**
	 * 将字符串解析为日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获得本天的开始时间，即2012-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentDayStartTime() {
		Date now = new Date();
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

}