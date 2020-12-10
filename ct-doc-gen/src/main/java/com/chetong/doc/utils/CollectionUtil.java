package com.chetong.doc.utils;

import java.util.List;

public class CollectionUtil {
	
	private CollectionUtil() {}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static boolean isNotEmpty(List<?> list) {
		return list != null && list.isEmpty();
	}
}
