package com.chetong.doc.exception;

/**
 * 业务枚举
 * 
 * @author 罗乔
 * @time 2020-12-12 16:53:12
 */
public enum ProcessCodeEnum {
	
	FAIL("fail", "失败");
	
	private String code;
	
	private String desc;
	
	private ProcessCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public CustomException buildProcessException(String msgAppend, Throwable... e) {
		if (e.length <=0) {
			return new CustomException(code, desc + " -> " + msgAppend);
		} else {
			return new CustomException(code, desc + " -> " + msgAppend, e[0]);
		}
	}
}
