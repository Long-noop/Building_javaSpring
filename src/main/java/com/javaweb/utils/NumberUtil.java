package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNumber(String data) {
		try {
			Integer value1 = Integer.parseInt(data);
			Long value2 = Long.parseLong(data);

		} catch (NumberFormatException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
