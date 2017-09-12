package com.zhb.test.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_PATTERN_WITHOUT_DELIMITER = "yyyyMMddHHmmss";
	//ISO 8601 RFC_3339 RFC2822
	public static final String RFC_3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";//for 2014-12-12T00:00:00.000+09:00 for 2001-07-04T12:08:56.235Z
	//RFC-822
	public static final String ISO_822_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";//2001-07-04T12:08:56.235-0700. for 2001-07-04T12:08:56.235 GMT

	public static void main(String[] args){
		String s = "2001-07-04T12:08:56.235 GMT";
		try {
			System.out.println(new SimpleDateFormat(ISO_822_PATTERN).parse(s));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Date parse(String dateStr) {
		try {
			return new SimpleDateFormat(DEFAULT_PATTERN).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	public static Date parse(String dateStr,String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String format(Date date) {
		return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
	}
	public static String format(Date date,String timePattern){
		if(date == null)
			return "";
		if(StringUtils.isEmpty(timePattern))
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
			return false;
		}
	}

	public static Date now() {
		return new Date();
	}
}
