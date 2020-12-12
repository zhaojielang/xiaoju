package com.chetong.doc.model;

/**
 * 资源路径
 * 
 * @author 罗乔
 * @time 2020-12-12 18:29:17
 */
public class SourcePath {

    /** Source path */
    private String path;

    /** path description */
    private String desc;
    
    public SourcePath(String path, String desc) {
    	this.path = path;
    	this.desc = desc;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
