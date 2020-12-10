package com.chetong.doc.model;

import java.util.List;

public class ApiDoc {
	
	/** 序号*/
	private int index;

    /** 文档名称 */
    private String name;

    /** 文档描述 */
    private String desc;
    
    /** Code 说明列表 */
    private List<ApiResultCode> apiResultCodes;
    
    /** Controller Api列表 */
    private List<ApiDocContent> controllerApiDocs;
    
    /** Service Api列表 */
    private List<ApiDocContent> serviceApiDocs;
    
    /** Api文本 */
    private String apiDocContent;
    
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

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

	public List<ApiResultCode> getApiResultCodes() {
		return apiResultCodes;
	}

	public void setApiResultCodes(List<ApiResultCode> apiResultCodes) {
		this.apiResultCodes = apiResultCodes;
	}

	public List<ApiDocContent> getControllerApiDocs() {
		return controllerApiDocs;
	}

	public void setControllerApiDocs(List<ApiDocContent> controllerApiDocs) {
		this.controllerApiDocs = controllerApiDocs;
	}

	public List<ApiDocContent> getServiceApiDocs() {
		return serviceApiDocs;
	}

	public void setServiceApiDocs(List<ApiDocContent> serviceApiDocs) {
		this.serviceApiDocs = serviceApiDocs;
	}

	public String getApiDocContent() {
		return apiDocContent;
	}

	public void setApiDocContent(String apiDocContent) {
		this.apiDocContent = apiDocContent;
	}
}
