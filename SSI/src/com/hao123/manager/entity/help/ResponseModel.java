package com.hao123.manager.entity.help;

public class ResponseModel {
	private int resultCode;
	private String resultMess;
	private Object resultObj;
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMess() {
		return resultMess;
	}
	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}
	public Object getResultObj() {
		return resultObj;
	}
	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}
}
