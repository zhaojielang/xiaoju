package com.hao123.ssh.entity.help;

import java.io.Serializable;

public class ResultModel<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2228493823227458354L;

	/**
	 * 标识符
	 */
	private String code;
	/**
	 * 提示信息
	 */
	private String message;
	/**
	 * 结果数据
	 */
	private Object resultModel;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResultModel() {
		return resultModel;
	}

	public void setResultModel(Object resultModel) {
		this.resultModel = resultModel;
	}
}
