package com.sample.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 현재 시간을 문자열 포맷 형태로 변환
	 * @param dateFormat : yyyyMMddHHmmss
	 * @return : 포맷대로 변환된 날짜 문자열
	 */
	public static String getFormatDate(String dateFormat) {
		Date date = new Date();
		return getFormatDate(date, dateFormat);
	}
	
	/**
	 * 날짜 객체를 문자열 포맷 형태로 변환
	 * @param date : 날짜객체
	 * @param dateFormat : yyyyMMddHHmmss
	 * @return : 포맷대로 변환된 날짜 문자열
	 */
	public static String getFormatDate(Date date, String dateFormat) {
		String result = "";
		
		if(date!=null && StringUtil.isNotEmpty(dateFormat)) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			result = format.format(date);
		}
		
		return result;
	}

}
