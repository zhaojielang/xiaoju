package com.chetong.doc.utils;

public class StringUtil {

	/**
	 * 判断为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		return str == null || str.toString().length() == 0;
	}
	
	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		return str != null && str.toString().replaceAll(" ", "").length() != 0;
	}
}
