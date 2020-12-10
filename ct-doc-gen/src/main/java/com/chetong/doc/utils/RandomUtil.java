package com.chetong.doc.utils;

/**
 * 随机工具类
 */
public class RandomUtil {
	
	private static final String NUMBER_ALETTER_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"; 
	
	/**
	 * 获取指定长度随机的字符串
	 * (包含：0-9 a-z A-Z)
	 * @param length 长度
	 * @return
	 * @author 罗乔
	 * @time 2019-8-26 16:58:26
	 */
	public static String createAllRandomCode(int length) {
		StringBuilder randomCode = new StringBuilder();
		int codeRangeLength = NUMBER_ALETTER_STR.length()-1;
		for (int i = 0; i < length; i++) {
			randomCode.append(NUMBER_ALETTER_STR.charAt((int)NumberUtil.getRandomByBetween(0, codeRangeLength)));
		}
		return randomCode.toString();
	}
}
