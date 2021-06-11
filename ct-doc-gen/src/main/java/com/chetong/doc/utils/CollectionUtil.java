package com.chetong.doc.utils;

import java.util.List;

/**
 * 集合工具类
 * 
 * @author luo
 * @time 2020-12-12 16:12:15
 */
public class CollectionUtil {
	
	private CollectionUtil() {}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static boolean isNotEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}
}
