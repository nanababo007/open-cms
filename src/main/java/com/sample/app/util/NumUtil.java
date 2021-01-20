package com.sample.app.util;

import java.util.Random;

public class NumUtil {

	public static int rand(int start, int end) {
		int result = 0;
		int scope = 0;
		
		start = Math.abs(start);
		end = Math.abs(end);
		
		scope = end - start;
		
		if(scope < 1) {
			return start;
		}
		
		Random rnd = new Random();
		result = rnd.nextInt(scope) + start;
		
		return result;
	}
	
}
