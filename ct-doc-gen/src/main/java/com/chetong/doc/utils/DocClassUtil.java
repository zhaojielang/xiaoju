package com.chetong.doc.utils;

import java.util.HashMap;
import java.util.Map;

import com.chetong.doc.constants.GlobalConstants;

public class DocClassUtil {
	
	public static final Map<String, String> primitiveClassSimpleName = new HashMap<>();
	public static final Map<String, String> baseClassSimpleName = new HashMap<>();
	public static final Map<String,String>  listClassSimpleName = new HashMap<>();
	public static final Map<String,String>  mapClassSimpleName = new HashMap<>();
	public static final Map<String,String>  jsonClassSimpleName = new HashMap<>();
	public static final Map<String,String>  isMvcIgnoreParamsType = new HashMap<>();
	protected static final String INT_TYPE_NAME = "int";
	protected static final String LONG_TYPE_NAME = "long";
	protected static final String FLOAT_TYPE_NAME = "float";
	protected static final String DOUBLE_TYPE_NAME = "double";
	protected static final String BOOLEAN_TYPE_NAME = "boolean";
	protected static final String CHAR_TYPE_NAME = "int";
	protected static final String STRING_TYPE_NAME = "string";
	protected static final String DATE_TYPE_NAME = "date";
	protected static final String MAP_TYPE_NAME = "map";
	protected static final String LIST_TYPE_NAME = "list";
	protected static final String PAGE_LIST_TYPE_NAME = "pagelist";
	protected static final String JSON_OBJ_TYPE_NAME = "jsonObj";
	protected static final String JSON_ARR_TYPE_NAME = "jsonArr";
	protected static final String FORMAT_TYPE_NAME = "format";
	protected static final String FORMAT_TYPE_PACKAGE = "java.text";
	protected static final String OBJ_TYPE_NAME = "obj";
	protected static final String COMMA = ",";
	protected static final String LEFT_ANGLE_BRACKETS = "<";
	protected static final String RIGHT_ANGLE_BRACKETS = ">";
	protected static final String LEFT_BRACKETS = "[";
	protected static final String RIGHT_BRACKETS = "]";
	protected static final String EMPTY_STR = "";
	protected static final String SPACE_KEY = " ";
	
	static {
		primitiveClassSimpleName.put("byte", INT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Byte", INT_TYPE_NAME);
		primitiveClassSimpleName.put("short", INT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Short", INT_TYPE_NAME);
		primitiveClassSimpleName.put("int", INT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Integer", INT_TYPE_NAME);
		primitiveClassSimpleName.put("long", LONG_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Long", LONG_TYPE_NAME);
		primitiveClassSimpleName.put(DOUBLE_TYPE_NAME, DOUBLE_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Double", DOUBLE_TYPE_NAME);
		primitiveClassSimpleName.put(FLOAT_TYPE_NAME, FLOAT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Float", FLOAT_TYPE_NAME);
		primitiveClassSimpleName.put("java.math.BigDecimal", DOUBLE_TYPE_NAME);
		primitiveClassSimpleName.put("java.math.BigInteger", LONG_TYPE_NAME);
		primitiveClassSimpleName.put(BOOLEAN_TYPE_NAME, BOOLEAN_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Boolean", BOOLEAN_TYPE_NAME);
		primitiveClassSimpleName.put("char", INT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.Character", CHAR_TYPE_NAME);
	}
	
	static {
		baseClassSimpleName.put("void", "null");
		baseClassSimpleName.put("java.lang.String", STRING_TYPE_NAME);
		baseClassSimpleName.put("java.util.Date", DATE_TYPE_NAME);
		baseClassSimpleName.put("java.sql.Timestamp", DATE_TYPE_NAME);
		baseClassSimpleName.put("java.time.LocalDateTime", DATE_TYPE_NAME);
		baseClassSimpleName.put("java.time.LocalDate", DATE_TYPE_NAME);
		baseClassSimpleName.put("java.util.Locale", DATE_TYPE_NAME);
		baseClassSimpleName.put("net.sf.json.JSONObject", JSON_OBJ_TYPE_NAME);
		baseClassSimpleName.put("net.sf.json.JSONArray", JSON_ARR_TYPE_NAME);
		baseClassSimpleName.put("com.google.gson.JsonObject", JSON_ARR_TYPE_NAME);
		baseClassSimpleName.put("com.google.gson.JsonArray", JSON_ARR_TYPE_NAME);
		baseClassSimpleName.put("com.alibaba.fastjson.JSONObject", JSON_OBJ_TYPE_NAME);
		baseClassSimpleName.put("com.alibaba.fastjson.JSONArray", JSON_ARR_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Object", OBJ_TYPE_NAME);
	}
	
	static {
		listClassSimpleName.put("java.util.List", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.LinkedList", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.ArrayList", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.Set", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.TreeSet", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.HasHset", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.SortedSet", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.Collection", LIST_TYPE_NAME);
		listClassSimpleName.put("java.util.ArrayDeque", LIST_TYPE_NAME);
		listClassSimpleName.put("com.chetong.aic.page.domain.PageList", PAGE_LIST_TYPE_NAME);
		listClassSimpleName.put("com.baobei.health.page.domain.PageList", PAGE_LIST_TYPE_NAME);
	}
	
	static {
		mapClassSimpleName.put("java.util.Map", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.SortedMap", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.TreeMap", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.LinkedHashMap", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.HashMap", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.HashSet", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.concurrent.ConcurrentHashMap", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.Properties", MAP_TYPE_NAME);
		mapClassSimpleName.put("java.util.HashTable", MAP_TYPE_NAME);
	}
	
	static {
		jsonClassSimpleName.put("net.sf.json.JSONObject", JSON_OBJ_TYPE_NAME);
		jsonClassSimpleName.put("net.sf.json.JSONArray", JSON_ARR_TYPE_NAME);
		jsonClassSimpleName.put("com.google.gson.JsonObject", JSON_ARR_TYPE_NAME);
		jsonClassSimpleName.put("com.google.gson.JsonArray", JSON_ARR_TYPE_NAME);
		jsonClassSimpleName.put("com.alibaba.fastjson.JSONObject", JSON_OBJ_TYPE_NAME);
		jsonClassSimpleName.put("com.alibaba.fastjson.JSONArray", JSON_ARR_TYPE_NAME);
	}
	
	static {
		isMvcIgnoreParamsType.put("org.springframework.ui.Model", OBJ_TYPE_NAME);
		isMvcIgnoreParamsType.put("org.springframework.ui.ModelMap", OBJ_TYPE_NAME);
		isMvcIgnoreParamsType.put("org.springframework.web.servlet.ModelAndView", OBJ_TYPE_NAME);
		isMvcIgnoreParamsType.put("org.springframework.validation.BindingResult", OBJ_TYPE_NAME);
		isMvcIgnoreParamsType.put("javax.servlet.http.HttpServletRequest", OBJ_TYPE_NAME);
		isMvcIgnoreParamsType.put("javax.servlet.http.HttpServletResponse", OBJ_TYPE_NAME);
	}
	
    public static String[] getGicName(String gicName) {
    	if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
    		return gicName.replaceAll(SPACE_KEY, EMPTY_STR).substring(gicName.indexOf(LEFT_ANGLE_BRACKETS)+1, gicName.length()-1).split(COMMA);
    	} else {
    		return new String[] {};
    	}
    }

    public static String getSimpleName(String gicName) {
        if (gicName.contains(LEFT_ANGLE_BRACKETS)) {
            return gicName.substring(0, gicName.indexOf(LEFT_ANGLE_BRACKETS));
        } else {
            return gicName;
        }
    }
    
    public static String getArraySimpleName(String arrName) {
    	return arrName.substring(0, arrName.indexOf(LEFT_BRACKETS));
    }
    
    public static String processTypeNameForParams(String javaTypeName) {
    	String typeName;
    	if (StringUtil.isEmpty(javaTypeName)) {
    		typeName = OBJ_TYPE_NAME;
		}else if (primitiveClassSimpleName.containsKey(javaTypeName)) {
        	typeName = primitiveClassSimpleName.get(javaTypeName);
        } else if (baseClassSimpleName.containsKey(javaTypeName)) {
        	typeName = baseClassSimpleName.get(javaTypeName);
        } else if (listClassSimpleName.containsKey(javaTypeName)) {
        	typeName = listClassSimpleName.get(javaTypeName);
        } else if (javaTypeName.contains(LEFT_BRACKETS)) {
        	typeName = "array("+processTypeNameForParams(getArraySimpleName(javaTypeName))+")";
        } else if (javaTypeName.startsWith(FORMAT_TYPE_PACKAGE)) {
        	typeName = FORMAT_TYPE_NAME;
		} else {
			typeName = OBJ_TYPE_NAME;
		}
        return typeName;
    }

    public static boolean isPrimitive(String type) {
    	return primitiveClassSimpleName.containsKey(type) 
    			|| baseClassSimpleName.containsKey(type)
    			|| type.startsWith(FORMAT_TYPE_PACKAGE);
    }

    public static boolean isGeneric(String type) {
    	return type.contains(LEFT_ANGLE_BRACKETS);
    }
    
    public static boolean isCollection(String type) {
        return listClassSimpleName.containsKey(type);
    }

    public static boolean isMap(String type) {
        return mapClassSimpleName.containsKey(type);
    }
    
    public static boolean isJson(String type) {
    	return jsonClassSimpleName.containsKey(type);
    }

    public static boolean isArray(String type){
        return type.contains("[]");
    }

    /**
     * check JSR303
     * @param annotationSimpleName annotation name
     * @return boolean
     */
    public static boolean isJSR303Required(String annotationSimpleName) {
        switch (annotationSimpleName) {
            case "NotNull":
                return true;
            case "NotEmpty":
                return true;
            case "NotBlank":
                return true;
            case "Required":
                return true;
            default:
                return false;
        }
    }

    public static boolean isRequiredTag(String tagName){
        return "required".equals(tagName);
    }

    public static boolean isIgnoreTag(String tagName){
        return GlobalConstants.IGNORE_TAG.equals(tagName);
    }

    public static boolean isMvcIgnoreParams(String paramType){
    	return isMvcIgnoreParamsType.containsKey(paramType);
    }
    
	public static String lowerFirstCapse(String str) {
		char[] chars = str.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
}