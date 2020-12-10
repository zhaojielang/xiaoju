package com.chetong.doc.exception;

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
