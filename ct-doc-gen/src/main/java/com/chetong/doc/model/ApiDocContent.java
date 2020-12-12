package com.chetong.doc.model;

import java.io.Serializable;

/**
 * API文档内容
 */
public class ApiDocContent implements Serializable {

	private static final long serialVersionUID = 6233912750739405802L;

	private String name;
	
	private String desc;

    private String url;
    
    private String serviceName;

    private String type;

    private String headers;

    private String contentType = "application/x-www-form-urlencoded";

    private String requestParams;

    private String requestUsage;

    private String responseUsage;

    private String responseParams;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponseUsage() {
        return responseUsage;
    }

    public void setResponseUsage(String responseUsage) {
        this.responseUsage = responseUsage;
    }

    public String getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(String responseParams) {
        this.responseParams = responseParams;
    }

    public String getRequestUsage() {
        return requestUsage;
    }

    public void setRequestUsage(String requestUsage) {
        this.requestUsage = requestUsage;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
