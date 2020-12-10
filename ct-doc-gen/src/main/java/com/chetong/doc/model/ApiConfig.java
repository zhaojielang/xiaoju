package com.chetong.doc.model;

import java.util.Arrays;
import java.util.List;

public class ApiConfig {

    /** 应用请求base路径 */
    private String basePathUrl;

    /** source path */
    private List<SourcePath> sourcePaths;

    /** 请求头 */
    private List<ApiReqHeader> requestHeaders;

    /** controller包过滤 */
    private String packageFilters;

    public String getBasePathUrl() {
		return basePathUrl;
	}

	public void setBasePathUrl(String basePathUrl) {
		this.basePathUrl = basePathUrl;
	}

    public List<ApiReqHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(ApiReqHeader... requestHeaders) {
    	this.requestHeaders = Arrays.asList(requestHeaders);
    }

	public void setSourcePaths(List<SourcePath> sourcePaths) {
		this.sourcePaths = sourcePaths;
	}

	public void setRequestHeaders(List<ApiReqHeader> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public List<SourcePath> getSourcePaths() {
        return sourcePaths;
    }

    public void setSourcePaths(SourcePath... sourcePaths) {
        this.sourcePaths = Arrays.asList(sourcePaths);
    }

    public String getPackageFilters() {
        return packageFilters;
    }

    public void setPackageFilters(String packageFilters) {
        this.packageFilters = packageFilters;
    }
}
