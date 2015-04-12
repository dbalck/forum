package com.forum.web.parse;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public abstract class Parser {
	
	private static final String RSS_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";

	public static long parseDate(String dateStr) {
		DateTime timestamp = new DateTime(dateStr);
		return timestamp.getMillis();
	}
	
	public static long parseDateRss(String dateStr) throws RuntimeException {
		DateFormat formatter = new SimpleDateFormat(RSS_DATE_PATTERN);
		Date d = null;
		try {
			d = formatter.parse(dateStr);
		} catch (Exception e) {
			System.out.println("Counldn't parse that date");
		}
		return d.getTime();
	}
	
	public static String buildDate(long timestamp) {
		Date d = new Date(timestamp);
		return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").format(d);
	}
	
	public abstract Stream parseLink(InputStream stream); 
	
	protected String validateLength(String str, int len) {
		if (str.length() > len) {
			str = str.substring(0, len - 1);
			return str;
		}
		return str;
	}

}
