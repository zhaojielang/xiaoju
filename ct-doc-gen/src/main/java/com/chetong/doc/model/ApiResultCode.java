package com.chetong.doc.model;

public class ApiResultCode {

	/** 枚举类名 */
	private String name;
	
	/** 枚举描述 */
	private String desc;
	
	/** 是否弃用 */
	private String isDeprecated = "false";;
	
	/** 枚举类名*/
	private String enumType;
	
    private String codeDesc;

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

	public String getIsDeprecated() {
		return isDeprecated;
	}

	public void setIsDeprecated(String isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
}
