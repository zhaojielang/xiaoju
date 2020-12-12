package com.chetong.doc.exception;

/**
 * 封装异常
 * 
 * @author 罗乔
 * @time 2020-12-12 16:52:39
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 5964112505575531396L;
	
	private final String errCode;

	public CustomException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}

	public CustomException(String errCode, String msg, Throwable cause) {
		super(msg,cause);
		this.errCode = errCode;
	}

	public String getErrorCode(){
		return errCode;
	}
}
