package com.bzn.fundamental.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 * 
 * @author：fengli
 * @since：2016年8月24日 下午4:12:15
 * @version:
 */
public class DateUtils {
	/*
	 * yyyy-MM-dd
	 */
	public static final String CN_DATE_FORMAT = "yyyy-MM-dd";

	/*
	 * MM月dd日
	 */
	public static final String CN_MONTHDAY_MD_FORMAT = "MM-dd";

	/*
	 * yyyt-MM-dd HH:mm
	 */
	public static final String CN_DATEMINITE_FORMAT = "yyyy-MM-dd HH:mm";

	/*
	 * yyyy年MM月dd日
	 */
	public static final String CN_DATEMINITE_YMD_FORMAT = "yyyy年MM月dd日";

	/*
	 * MM月dd日
	 */
	public static final String CN_DATEMINITE_MD_FORMAT = "MM月dd日";

	/*
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String CN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * yyyyMMddHHmmss
	 */
	public static final String STRING_DATE_FORMAT = "yyyyMMddHHmmss";

	/*
	 * yyyyMMddHHmmssSSS
	 */
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	/*
	 * yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	/*
	 * yyyy/MM/dd
	 */
	public static final String CN_SPRIT_DATE_FORMAT_DAY = "yyyy/MM/dd";

	/*
	 * yyyy/MM/dd HH
	 */
	public static final String CN_SPRIT_DATE_FORMAT_HOUR = "yyyy/MM/dd HH";

	/*
	 * yyyy/MM/dd HH:mm
	 */
	public static final String CN_SPRIT_DATE_FORMAT_MINUTE = "yyyy/MM/dd HH:mm";

	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat yyyyMMdd_ = new SimpleDateFormat(
			"yyyyMMdd");
	private static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 转换String时间为Date
	 * 
	 * @param ds
	 * @return
	 */
	public static Date parseString(String ds) {
		Date d = null;
		try {
			d = yyyyMMdd_.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 转换Date时间为String
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		String d = "";
		try {
			d = yyyyMMdd.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
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
	 * 根据格式获取指定日期格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 将毫秒数格式化为年月日，返回如 2015-05-15
	 * 
	 * @param time
	 * @return
	 */
	public static String formatToYMD(Long time) {
		return formatDate(new Date(time), CN_DATE_FORMAT);
	}

	/**
	 * 将毫秒数格式化为年月日，返回如 2015-05-15
	 * 
	 * @param time
	 * @return
	 */
	public static String formatToYMD(Long time, String format) {
		return formatDate(new Date(time), format);
	}

	/**
	 * 获取当前毫秒数
	 * 
	 * @return
	 */
	public static Long getCurrentDateTime() {
		return getDateTime(new Date());
	}

	/**
	 * 根据制定日期获取毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static Long getDateTime(Date date) {
		return date.getTime();
	}

	/**
	 * 将毫秒数 加上 指定天数后 返回毫秒数
	 * 
	 * @param time 毫秒数
	 * @param day 天数
	 * @return 毫秒数
	 */
	public static Long addDay(Long time, Integer day) {
		return time + getTime(day);
	}

	/**
	 * 将天数转换成毫秒数
	 * 
	 * @param day 天数
	 * @return 毫秒数
	 */
	public static Long getTime(Integer day) {
		return day * 3600L * 24 * 1000;
	}

	/**
	 * 添加月份
	 * 
	 * @param time
	 * @param month
	 * @return
	 */
	public static Long addMonth(Long time, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取当前日期毫秒数(不含时分秒)
	 * 
	 * @return
	 */
	public static Long getCurrentDate() {
		return getYTDDate(System.currentTimeMillis());
	}

	/**
	 * 去除时间毫秒数中的分时秒
	 * 
	 * @param time
	 * @return
	 */
	public static Long getYTDDate(Long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0L);
		c.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 获取星期几
	 * 
	 * @param time
	 * @return
	 */
	public static int getWeek(Long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 判断是否为周末
	 * 
	 * @param time
	 * @return
	 */
	public static Boolean isWeekend(Long time) {
		Boolean flag = Boolean.FALSE;
		int week = getWeek(time);
		week++;
		if (week == Calendar.SUNDAY || week == Calendar.SATURDAY) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	/**
	 * 是否为周六
	 * 
	 * @param time
	 * @return
	 */
	public static Boolean isSaturday(Long time) {
		Boolean flag = Boolean.FALSE;
		int week = getWeek(time);
		week++;
		if (week == Calendar.SATURDAY) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	/**
	 * 是否为周日
	 * 
	 * @param time
	 * @return
	 */
	public static Boolean isSunday(Long time) {
		Boolean flag = Boolean.FALSE;
		int week = getWeek(time);
		week++;
		if (week == Calendar.SUNDAY) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	/**
	 * 获取系统时间：字符串
	 * 
	 * @param formater
	 * @return
	 */
	public static String getSysStr(String formater) {
		return formatDate(new Date(), formater);
	}
	
	/**
	 * 获取系统时间：字符串
	 * 
	 * @param yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String getSysStr() {
		return formatDate(new Date(), DateUtils.CN_DATETIME_FORMAT);
	}

	/**
	 * 获取当前月的第一天(毫秒数)
	 * 
	 * @return
	 */
	public static long getMillisFirstDayOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Long data) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(data);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Long data) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(data);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Long data) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(data);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 将日期字符串转换为毫秒数
	 * 
	 * @param ymd 日期如:2015-10-15
	 * @return
	 */
	public static Long getMillisByYMD(String ymd) {
		SimpleDateFormat format = new SimpleDateFormat(CN_DATE_FORMAT);
		try {
			Date dt = format.parse(ymd);
			return dt.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 将日期字符串转换为毫秒数
	 * 
	 * @param ymd 日期如:2015-10-15 / 2015/10/15
	 * @param ft 日期格式
	 * @return
	 */
	public static Long getMillisByYMD(String ymd, String ft) {
		SimpleDateFormat format = new SimpleDateFormat(ft);
		try {
			Date dt = format.parse(ymd);
			return dt.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
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

	/**
	 * 获得本天的结束时间，即2012-01-01 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentDayEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前时间距当天最晚时间 23:59:59 相差的秒数
	 * 
	 * @return
	 */
	public static long getNowToEndTime() {
		long now = new Date().getTime();
		long endtime = getCurrentDayEndTime().getTime();
		long second = (endtime - now) / 1000;
		return second;
	}
	
	
	/**
	 * 投保单延迟2天
	 * @param time
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Calendar delayTwoDay(long time,int num) throws ParseException {
		Calendar cal = Calendar.getInstance();
		Date date=DateUtils.yyyyMMdd.parse(DateUtils.formatToYMD(DateUtils.addDay(time,num)));
		cal.setTime(date);
		return cal;
	}
	/**
	 * 投保单延迟2天在延迟一年减一天
	 * @param time
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Calendar delayOneYear(long time,int num) throws ParseException {
		Calendar cal = Calendar.getInstance();  
		Date dates=DateUtils.yyyyMMdd.parse(DateUtils.formatToYMD(DateUtils.addDay(time,num)));
		cal.setTime(dates);
		cal.add(Calendar.YEAR,1);   
		return cal;
	}

}
