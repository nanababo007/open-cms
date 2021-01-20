package com.sample.app.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		boolean result = true;
		
		if(str!=null && !str.trim().equals("")) {
			result = false;
		}
		
		return result;
	}
	
	public static boolean isEmpty(Object str) {
		boolean result = true;
		
		if(str!=null) {
			result = isEmpty(str.toString());
		}
		
		return result;
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}
	
	public static String nvl(String str) {
		return str==null ? "" : str;
	}
	
	public static String nvl(Object str) {
		return str==null ? "" : str.toString();
	}
	
}
