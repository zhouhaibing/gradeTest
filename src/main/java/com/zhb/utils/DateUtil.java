package com.zhb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_PATTERN_WITHOUT_DELIMITER = "yyyyMMddHHmmss";
	public static final String RFC_3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";//for 2014-12-12T00:00:00.000+09:00.or ISO 8601
	public static final String ISO_822_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";//2001-07-04T12:08:56.235-0700

	public static Date parse(String dateStr) {
		try {
			return new SimpleDateFormat(DEFAULT_PATTERN).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parse(String dateStr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parse(String dateStr, String pattern, TimeZone timeZone) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(timeZone);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String format(Date date) {
		return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
	}

	public static String format(Date date, String timePattern) {
		if (date == null)
			return null;
		if (StringUtils.isEmpty(timePattern))
			return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
		return new SimpleDateFormat(timePattern).format(date);
	}

	public static boolean withinOneMonth(Date earlyDate, Date laterDate) {
		if (earlyDate == null || laterDate == null) {
			return false;
		}
		return (Math.abs(earlyDate.getTime() - laterDate.getTime()) <= 30l * 24l * 60 * 60 * 1000);
	}

	public static boolean withinOneDay(Date orderDate, String requestDateString, String pattern) {
		if (orderDate == null || requestDateString == null) {
			return false;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date requestDate = format.parse(requestDateString);
			return (Math.abs(orderDate.getTime() - requestDate.getTime()) <= 24l * 60 * 60 * 1000);
		} catch (Throwable t) {
			//XGLog.supplementExceptionMessage(t);
			return false;
		}
	}

	public static boolean withinSevenDay(Date orderDate, Date requestDate) {
		if (orderDate == null || requestDate == null) {
			return false;
		}
		try {
			return (Math.abs(orderDate.getTime() - requestDate.getTime()) <= 7 * 24 * 60 * 60 * 1000L);
		} catch (Throwable t) {
			//XGLog.supplementExceptionMessage(t);
			return false;
		}
	}

	public static Date now() {
		return new Date();
	}
	
	public static void main(String[] args){
		long time = 1482992561300L;
		System.out.println((time % (1000*3600*24)) / 3600000);
		System.out.println((time % (1000*3600*24)) % 3600000);
		Date date = new Date(time);
		System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
