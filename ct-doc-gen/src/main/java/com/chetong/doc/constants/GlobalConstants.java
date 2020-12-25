package com.chetong.doc.constants;

import java.util.HashMap;
import java.util.Map;

public class GlobalConstants {
	
	public static final Map<String,String> CONTROLLER_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> SERVICE_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> REQUEST_MAPPING_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> REQUIRED_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> LOG_TAIL_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> IGNORE_CLASS = new HashMap<>();
	
	/** 忽略注解名称 */
	public static final String IGNORE_TAG = "ignore";
	
	/** BaseEnum注解名称 */
	public static final String BASE_ENUM = "BaseEnum";
	
	/** CT BaseEnum注解全名称 */
	public static final String BASE_ENUM_FULLY_CT = "com.chetong.aic.enums.BaseEnum";
	
	/** BB BaseEnum注解全名称 */
	public static final String BASE_ENUM_FULLY_BB = "com.baobei.health.enums.BaseEnum";

	/** Controller注解全名称 */
    public static final String CONTROLLER_FULLY = "org.springframework.stereotype.Controller";
    
    /** RestController注解全名称 */
    public static final String RESTCONTROLLER_FULLY = "org.springframework.web.bind.annotation.RestController";
    
	/** RequestMapping注解全名称 */
	public static final String REQUEST_MAPPING_FULLY = "org.springframework.web.bind.annotation.RequestMapping";
	
	/** PostMapping注解全名称 */
	public static final String POST_MAPPING_FULLY = "org.springframework.web.bind.annotation.PostMapping";
	
	/** GetMapping注解全名称 */
	public static final String GET_MAPPING_FULLY = "org.springframework.web.bind.annotation.GetMapping";
	
	/** ModelAndView注解名称 */
	public static final String MODEL_AND_VIEW = "org.springframework.web.servlet.ModelAndView";
	
	/** ModelAndView注解全名称 */
	public static final String MODEL_AND_VIEW_FULLY = "org.springframework.web.servlet.ModelAndView";

	/** Service注解全名称 */
	public static final String SERVICE_FULLY = "org.springframework.stereotype.Service";

	/** LogTail注解全名称 */
	public static final String LOG_TAIL_FULLY = "com.chetong.aic.annotation.LogTail";

	/** ParamRequired注解全名称 */
	public static final String PARAM_REQUIRED_FULLY = "com.chetong.aic.annotation.ParamRequired";
	
	/** 基础Model名称 */
	public static final String BASE_MODEL_FULLY = "com.chetong.aic.entity.base.BaseModel";
	
	/** HTTP 请求体 */
	public static final String HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";
	
	/** HTTP 响应体 */
	public static final String HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse";
	
	static {
		CONTROLLER_ANNOTATION.put(GlobalConstants.CONTROLLER_FULLY,GlobalConstants.CONTROLLER_FULLY);
		CONTROLLER_ANNOTATION.put(GlobalConstants.RESTCONTROLLER_FULLY,GlobalConstants.RESTCONTROLLER_FULLY);
	}
	
	static {
		SERVICE_ANNOTATION.put(GlobalConstants.SERVICE_FULLY,GlobalConstants.SERVICE_FULLY);
	}
	
	static {
		REQUEST_MAPPING_ANNOTATION.put(GlobalConstants.REQUEST_MAPPING_FULLY,GlobalConstants.REQUEST_MAPPING_FULLY);
		REQUEST_MAPPING_ANNOTATION.put(GlobalConstants.POST_MAPPING_FULLY,GlobalConstants.POST_MAPPING_FULLY);
		REQUEST_MAPPING_ANNOTATION.put(GlobalConstants.GET_MAPPING_FULLY,GlobalConstants.GET_MAPPING_FULLY);
	}
	
	static {
		REQUIRED_ANNOTATION.put(GlobalConstants.PARAM_REQUIRED_FULLY,GlobalConstants.PARAM_REQUIRED_FULLY);
	}
	
	static {
		LOG_TAIL_ANNOTATION.put(GlobalConstants.LOG_TAIL_FULLY,GlobalConstants.LOG_TAIL_FULLY);
	}
	
	static {
		IGNORE_CLASS.put(GlobalConstants.BASE_MODEL_FULLY,GlobalConstants.BASE_MODEL_FULLY);
		IGNORE_CLASS.put(GlobalConstants.HTTP_SERVLET_REQUEST,GlobalConstants.HTTP_SERVLET_REQUEST);
		IGNORE_CLASS.put(GlobalConstants.HTTP_SERVLET_RESPONSE,GlobalConstants.HTTP_SERVLET_RESPONSE);
	}
}
