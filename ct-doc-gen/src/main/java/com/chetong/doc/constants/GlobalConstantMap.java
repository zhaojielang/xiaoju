package com.chetong.doc.constants;

import java.util.HashMap;
import java.util.Map;

public class GlobalConstantMap {

	public static final Map<String,String> CONTROLLER_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> SERVICE_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> REQUEST_MAPPING_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> REQUIRED_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> LOG_TAIL_ANNOTATION = new HashMap<>();
	
	public static final Map<String,String> IGNORE_CLASS = new HashMap<>();
	
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
