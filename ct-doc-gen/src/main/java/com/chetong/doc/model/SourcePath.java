package com.chetong.doc.model;

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
