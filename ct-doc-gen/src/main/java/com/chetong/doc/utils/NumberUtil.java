package com.chetong.doc.utils;

import java.text.DecimalFormat;

import com.chetong.doc.exception.ProcessCodeEnum;

/**
 * 数字工具类
 * 
 * @author 罗乔
 * @time 2019-3-18 20:21:18
 *
 */
public class NumberUtil {
	
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0000");
    
    private NumberUtil() {
    }

	/**
	 * 生成指定位数随机数
	 *  
	 * 注：最小1位，最大仅支持19位
	 * 
	 * @param length 位数
	 * @return
	 * @author 罗乔
	 * @time 2019-5-27 13:41:16
	 */
	public static long getRandomByLength(int length) {
		if (0<length && length<20) {
			double min = Math.pow(10, length-1D);
			double max = Math.pow(10, length);
			long random = Math.round(Math.random()*max);
			while (random < min) {
				random = Math.round(Math.random()*max);
			}
			return random;
		} else {
			throw ProcessCodeEnum.FAIL.buildProcessException("仅支持1到19位的随机数");
		}
	}
	
	/**
	 * 获取min到max的随机数，包含max
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @author 罗乔
	 * @time 2019-5-27 13:41:29
	 */
	public static long getRandomByBetween(long min, long max) {
		return (long)(Math.random()*(max-min+1))+min;
	}
	
	/**
	 * 获取min到max的随机数，包含max
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @author 罗乔
	 * @time 2019-5-27 13:41:29
	 */
	public static double getDoubleRandomByBetween(long min, long max) {
		return (Math.random()*(max-min+1))+min;
	}
	
	/**
	 * 生成指定位数随机数
	 *  
	 * 注：最小1位，最大仅支持19位
	 * 
	 * @param length 位数
	 * @return
	 * @author 罗乔
	 * @time 2019-5-27 13:41:16
	 */
	public static double getDoubleRandomByLength(int length) {
		if (0<length && length<20) {
			double min = Math.pow(10, length-1D);
			double max = Math.pow(10, length);
			double random = Math.random()*max;
			while (random < min) {
				random = Math.random()*max;
			}
			return random;
		} else {
			throw ProcessCodeEnum.FAIL.buildProcessException("仅支持1到19位的随机数");
		}
	}

	/**
	 * string --> long
	 * @param str
	 * @return
	 */
	public static Long stringToLong(String str){
		if(str==null || "".equals(str.trim())){
			return null;
		}
		return Long.valueOf(str);
	}
	
	public static Float stringToFloat(String str,Float dft){
		if(str==null||"".equals(str.trim())){
			if(StringUtil.isEmpty(dft)){
				return null;
			}else{
				return dft;
			}
		}
		return Float.valueOf(str);
	}
	
	public static Integer stringToInteger(String str,Integer dft){
		if(str==null||"".equals(str.trim())){
			if(StringUtil.isEmpty(dft)){
				return null;
			}else{
				return dft;
			}
		}
		return Integer.valueOf(str);
	}
}
