package com.bzn.fundamental.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * yyyyMMddHHmm
	 */
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

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

	public static String DATE_FORMAT_FULL_STRING = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT_DATE_STRING = "yyyy-MM-dd";
	public static SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(CN_DATE_FORMAT);

	/**
	 * 转换String时间为Date
	 * 
	 * @param ds
	 * @return
	 */
	public static Date parseString(String ds) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			d = sdf.parse(ds);
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
			SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
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
	 * 返回指定日期的月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
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
	 * 将日期字符串添加day天
	 * 
	 * @param time 毫秒数
	 * @param day 天数
	 * @return 毫秒数
	 */
	public static String addDay(String time, Integer day) {
		return DateUtils.formatToYMD(
				(DateUtils.getMillisByYMD(time, DateUtils.CN_DATETIME_FORMAT) + getTime(day)),
				DateUtils.CN_DATETIME_FORMAT);
	}
	
	/**
	 * 将指定日期添加day天,默认在当天添加
	 * 
	 * @return
	 */
	public static Date addDay(Integer day,Date date) {
		Calendar cal = Calendar.getInstance();
		if(date == null){
			date = new Date();
		}
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	
	/**
	 * 添加分钟数
	 * 
	 * @param time
	 * @param minute
	 * @return
	 */
	public static String addMinute(String time, Integer minute) {
		return DateUtils.formatToYMD((DateUtils.getMillisByYMD(time, DateUtils.CN_DATETIME_FORMAT)
				+ getMinuteTime(minute)), DateUtils.CN_DATETIME_FORMAT);
	}

	private static Long getMinuteTime(Integer minute) {
		return minute * 60 * 1000L;
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
		format.setLenient(false);
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			now = sdf.parse(sdf.format(now));
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			now = longSdf.parse(sdf.format(now) + " 23:59:59");
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
	 * 
	 * @param time
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Calendar delayTwoDay(long time, int num) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(DateUtils.formatToYMD(DateUtils.addDay(time, num)));
		cal.setTime(date);
		return cal;
	}

	/**
	 * 投保单延迟2天在延迟一年减一天
	 * 
	 * @param time
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Calendar delayOneYear(long time, int num) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dates = sdf.parse(DateUtils.formatToYMD(DateUtils.addDay(time, num)));
		cal.setTime(dates);
		cal.add(Calendar.YEAR, 1);
		return cal;
	}

	/**
	 * 获取昨天
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 获取指定日期的第一秒 e.g. 2016-11-02 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayFirstSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		return cal.getTime();
	}

	/**
	 * date转string yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		String d = "";
		try {
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			d = longSdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 获取指定日期的最后一秒 e.g. 2016-11-02 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayLastSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		return cal.getTime();
	}

	/**
	 * 校验日期格式是否正确(根据要求设置1988/11/16)
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean isValidDate(String dateStr) {
		if (null == dateStr || "".equals(dateStr)) {
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.CN_SPRIT_DATE_FORMAT_DAY);
		try {
			format.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))"
				+ "|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))"
				+ ")";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}

	/**
	 * 判断日期大小
	 * 
	 * @param srcDate 源日期
	 * @param descDate 目标日期
	 * @return 0: srcDate等于descDate, 1: srcDate大于descDate, -1: srcDate小于descDate
	 */
	public static int compareTo(Date srcDate, Date descDate) {
		Calendar srcDateC = getCalendar(srcDate);
		Calendar descDateC = getCalendar(descDate);

		int srcYear = srcDateC.get(Calendar.YEAR);
		int descYear = descDateC.get(Calendar.YEAR);
		if (srcYear == descYear) {
			int srcDayOfYear = srcDateC.get(Calendar.DAY_OF_YEAR);
			int descDayOfYear = descDateC.get(Calendar.DAY_OF_YEAR);
			if (srcDayOfYear == descDayOfYear) {
				return 0;
			} else {
				return srcDayOfYear > descDayOfYear ? 1 : -1;
			}
		} else {
			return srcYear > descYear ? 1 : -1;
		}
	}

	public static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}

	/**
	 * @return format:yyyyMMddHHmmss
	 */
	public static String getFullCurrentTimeNoSeparator() {
		return new SimpleDateFormat(STRING_DATE_FORMAT).format(new Date());
	}

	public static String format(Date date, String pattern) {
		return format(date, new SimpleDateFormat(pattern));
	}

	public static String format(String srcDate, String srcPattern, String descPattern) {
		return format(parse(srcDate, srcPattern), descPattern);
	}

	public static String format(Date date, DateFormat df) {
		if (date == null)
			return null;
		return df.format(date);
	}

	public static String getTodayEndTime() {
		return DATE_FORMAT_DATE.format(new Date()) + " 23:59:59";
	}

	public static Date offset(Date date, int field, int amount) {
		if (date == null)
			return null;
		Calendar newDate = getCalendar(date);
		newDate.add(field, amount);

		return newDate.getTime();
	}

	public static Date offsetYears(Date date, int amount) {
		return offset(date, Calendar.YEAR, amount);
	}

	public static int getYear(Date date) {
		return getCalendar(date).get(Calendar.YEAR);
	}

	public static boolean isLeapYear(Date date) {
		int year = getYear(date);

		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int getLastDayOfYear(Date date) {
		return isLeapYear(date) ? 366 : 365;
	}

	public static int getInterval(Date fromDate, Date toDate, int field) {
		Calendar fromDateC = getCalendar(fromDate);
		Calendar toDateC = getCalendar(toDate);
		if (toDateC.before(fromDate)) {
			return 0;
		}

		if (Calendar.YEAR == field) {
			return toDateC.get(Calendar.YEAR) - fromDateC.get(Calendar.YEAR);
		} else if (Calendar.MONTH == field) {
			int months = 0;
			int intervalYears = (toDateC.get(Calendar.YEAR) - fromDateC.get(Calendar.YEAR));
			if (intervalYears == 0) {
				months = toDateC.get(Calendar.MONTH) - fromDateC.get(Calendar.MONTH);
			} else {
				int months_h = (11 - fromDateC.get(Calendar.MONTH));
				int months_f = toDateC.get(Calendar.MONTH) + 1;
				int months_m = intervalYears <= 1 ? 0 : ((intervalYears - 1) * 12);

				months = (months_h + months_m + months_f);
			}

			return months;
		} else if (Calendar.DATE == field) {
			int days = 0;
			int intervalYears = (toDateC.get(Calendar.YEAR) - fromDateC.get(Calendar.YEAR));
			if (intervalYears == 0) {
				days = toDateC.get(Calendar.DAY_OF_YEAR) - fromDateC.get(Calendar.DAY_OF_YEAR);
			} else {
				int days_h = getLastDayOfYear(fromDate) - fromDateC.get(Calendar.DAY_OF_YEAR);
				int days_f = toDateC.get(Calendar.DAY_OF_YEAR);
				int days_m = 0;
				if (intervalYears > 1) {
					Date tmpDate = null;
					for (int i = 1; i < intervalYears; i++) {
						tmpDate = offsetYears(fromDate, 1);
						days_m += getLastDayOfYear(tmpDate);
					}
				}

				days = (days_h + days_m + days_f);
			}

			return days;
		}

		return 0;
	}

	public static int getIntervalDays(Date fromDate, Date toDate) {
		return getInterval(fromDate, toDate, Calendar.DATE);
	}

	public static int getIntervalMonths(Date fromDate, Date toDate) {
		return getInterval(fromDate, toDate, Calendar.MONTH);
	}

	public static int getIntervalYears(Date fromDate, Date toDate) {
		return getInterval(fromDate, toDate, Calendar.YEAR);
	}

	public static int birthdayToAge(Date date) {
		return getIntervalYears(date, new Date());
	}
}
