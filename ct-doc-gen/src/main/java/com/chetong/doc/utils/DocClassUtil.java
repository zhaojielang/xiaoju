package com.chetong.doc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocClassUtil {
	
	protected static final Map<String, String> baseClassSimpleName = new HashMap<>();
	protected static final Map<String, String> primitiveClassSimpleName = new HashMap<>();
	protected static final Map<String,String>  listClassSimpleName = new HashMap<>();
	protected static final Map<String,String>  mapClassSimpleName = new HashMap<>();
	protected static final Map<String,String>  jsonClassSimpleName = new HashMap<>();
	protected static final String INT_TYPE_NAME = "int";
	protected static final String LONG_TYPE_NAME = "long";
	protected static final String FLOAT_TYPE_NAME = "float";
	protected static final String DOUBLE_TYPE_NAME = "double";
	protected static final String BOOLEAN_TYPE_NAME = "boolean";
	protected static final String STRING_TYPE_NAME = "string";
	protected static final String DATE_TYPE_NAME = "date";
	protected static final String MAP_TYPE_NAME = "map";
	protected static final String LIST_TYPE_NAME = "list";
	protected static final String PAGE_LIST_TYPE_NAME = "pagelist";
	protected static final String JSON_OBJ_TYPE_NAME = "jsonObj";
	protected static final String JSON_ARR_TYPE_NAME = "jsonArr";
	protected static final String FORMAT_TYPE_NAME = "format";
	protected static final String OBJ_TYPE_NAME = "obj";
	protected static final String COMMA = ",";
	protected static final String LEFT_ANGLE_BRACKETS = "<";
	protected static final String RIGHT_ANGLE_BRACKETS = ">";
	
	static {
		baseClassSimpleName.put("java.lang.Integer", INT_TYPE_NAME);
		baseClassSimpleName.put("Integer", INT_TYPE_NAME);
		baseClassSimpleName.put("int", INT_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Long", LONG_TYPE_NAME);
		baseClassSimpleName.put("Long", LONG_TYPE_NAME);
		baseClassSimpleName.put("long", LONG_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Double", DOUBLE_TYPE_NAME);
		baseClassSimpleName.put("Double", DOUBLE_TYPE_NAME);
		baseClassSimpleName.put(DOUBLE_TYPE_NAME, DOUBLE_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Float", FLOAT_TYPE_NAME);
		baseClassSimpleName.put("Float", FLOAT_TYPE_NAME);
		baseClassSimpleName.put(FLOAT_TYPE_NAME, FLOAT_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Short", INT_TYPE_NAME);
		baseClassSimpleName.put("Short", INT_TYPE_NAME);
		baseClassSimpleName.put("short", INT_TYPE_NAME);
		baseClassSimpleName.put("java.math.BigDecimal", DOUBLE_TYPE_NAME);
		baseClassSimpleName.put("BigDecimal", DOUBLE_TYPE_NAME);
		baseClassSimpleName.put("java.math.BigInteger", LONG_TYPE_NAME);
		baseClassSimpleName.put("BigInteger", LONG_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Boolean", BOOLEAN_TYPE_NAME);
		baseClassSimpleName.put("Boolean", BOOLEAN_TYPE_NAME);
		baseClassSimpleName.put(BOOLEAN_TYPE_NAME, BOOLEAN_TYPE_NAME);
		baseClassSimpleName.put("java.lang.Byte", INT_TYPE_NAME);
		baseClassSimpleName.put("Byte", INT_TYPE_NAME);
		baseClassSimpleName.put("byte", INT_TYPE_NAME);
	}
	
	static {
		primitiveClassSimpleName.put("java.lang.Character", INT_TYPE_NAME);
		primitiveClassSimpleName.put("Character", INT_TYPE_NAME);
		primitiveClassSimpleName.put("Char", INT_TYPE_NAME);
		primitiveClassSimpleName.put("char", INT_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.String", STRING_TYPE_NAME);
		primitiveClassSimpleName.put("String", STRING_TYPE_NAME);
		primitiveClassSimpleName.put("java.util.Date", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("Date", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("java.sql.Timestamp", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("Timestamp", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("java.time.LocalDateTime", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("LocalDateTime", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("java.time.LocalDate", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("LocalDate", DATE_TYPE_NAME);
		primitiveClassSimpleName.put("java.util.Map", MAP_TYPE_NAME);
		primitiveClassSimpleName.put("Map", MAP_TYPE_NAME);
		primitiveClassSimpleName.put("com.alibaba.fastjson.JSONObject", JSON_OBJ_TYPE_NAME);
		primitiveClassSimpleName.put("net.sf.json.JSONObject", JSON_OBJ_TYPE_NAME);
		primitiveClassSimpleName.put("JSONObject", JSON_OBJ_TYPE_NAME);
		primitiveClassSimpleName.put("com.alibaba.fastjson.JSONArray", JSON_ARR_TYPE_NAME);
		primitiveClassSimpleName.put("net.sf.json.JSONArray", JSON_ARR_TYPE_NAME);
		primitiveClassSimpleName.put("JSONArray", JSON_ARR_TYPE_NAME);
		primitiveClassSimpleName.put("java.lang.object", OBJ_TYPE_NAME);
		primitiveClassSimpleName.put("Object", OBJ_TYPE_NAME);
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
		listClassSimpleName.put("PageList", PAGE_LIST_TYPE_NAME);
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
		jsonClassSimpleName.put("com.alibaba.fastjson.JSONObject", JSON_OBJ_TYPE_NAME);
		jsonClassSimpleName.put("net.sf.json.JSONObject", JSON_OBJ_TYPE_NAME);
		jsonClassSimpleName.put("JSONObject", JSON_OBJ_TYPE_NAME);
		jsonClassSimpleName.put("com.alibaba.fastjson.JSONArray", JSON_ARR_TYPE_NAME);
		jsonClassSimpleName.put("net.sf.json.JSONArray", JSON_ARR_TYPE_NAME);
		jsonClassSimpleName.put("JSONArray", JSON_ARR_TYPE_NAME);
	}
	
    /**
     * get class names by generic class name
     *
     * @param returnType generic class name
     * @return array of string
     */
    public static String[] getSimpleGicName(String returnType) {
        if (returnType.indexOf(LEFT_ANGLE_BRACKETS) != -1) {
            String pre = returnType.substring(0, returnType.indexOf(LEFT_ANGLE_BRACKETS));
            if (DocClassUtil.isMap(pre)) {
                return getMapKeyValueType(returnType);
            }
            String[] arr;
            String type = returnType.substring(returnType.indexOf(LEFT_ANGLE_BRACKETS) + 1, returnType.lastIndexOf(RIGHT_ANGLE_BRACKETS));
            if (isCollection(getSimpleName(type)) || isMap(getSimpleName(type))) {
                return new String[] {type};
            } else {
				arr = type.split(COMMA);
			}
            return classNameFix(arr);
        } else {
            return returnType.split(" ");
        }
    }

    /**
     * Get a simple type name from a generic class name
     *
     * @param gicName Generic class name
     * @return String
     */
    public static String getSimpleName(String gicName) {
        if (gicName.indexOf(LEFT_ANGLE_BRACKETS) != -1) {
            return gicName.substring(0, gicName.indexOf(LEFT_ANGLE_BRACKETS));
        } else {
            return gicName;
        }
    }
    
    public static String getGlobGicSimpleName(String gicName) {
        if (gicName.indexOf(LEFT_ANGLE_BRACKETS) != -1) {
            return gicName.substring(gicName.indexOf(LEFT_ANGLE_BRACKETS)+1, gicName.length()-1);
        } else {
            return gicName;
        }
    }

    /**
     * Automatic repair of generic split class names
     *
     * @param arr arr of class name
     * @return array of String
     */
    private static String[] classNameFix(String[] arr) {
        List<String> classes = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        int globIndex = 0;
        for (int i = 0; i < arr.length; i++) {
        	arr[i] = arr[i].replaceAll(" ", "");
            if (!classes.isEmpty()) {
                int index = classes.size() - 1;
                if (!DocUtil.isClassName(classes.get(index))) {
                    globIndex = globIndex + 1;
                    if (globIndex < arr.length) {
                        indexList.add(globIndex);
                        String className = classes.get(index) + COMMA + arr[globIndex];
                        classes.set(index, className);
                    }

                } else {
                    globIndex = globIndex + 1;
                    if (globIndex < arr.length) {
                        if (DocUtil.isClassName(arr[globIndex])) {
                            indexList.add(globIndex);
                            classes.add(arr[globIndex]);
                        } else {
                            if (!indexList.contains(globIndex) && !indexList.contains(globIndex + 1)) {
                                indexList.add(globIndex);
                                classes.add(arr[globIndex] + COMMA + arr[globIndex + 1]);
                                globIndex = globIndex + 1;
                                indexList.add(globIndex);
                            }
                        }
                    }
                }
            } else {
                if (DocUtil.isClassName(arr[i])) {
                    indexList.add(i);
                    classes.add(arr[i]);
                } else {
                    if (!indexList.contains(i) && !indexList.contains(i + 1)) {
                        globIndex = i + 1;
                        classes.add(arr[i] + COMMA + arr[globIndex]);
                        indexList.add(i);
                        indexList.add(i + 1);
                    }
                }
            }
        }
        return classes.toArray(new String[classes.size()]);
    }

    /**
     * get map key and value type name populate into array.
     *
     * @param gName generic class name
     * @return array of string
     */
    public static String[] getMapKeyValueType(String gName) {
        if(gName.indexOf(LEFT_ANGLE_BRACKETS) != -1){
            String[] arr = new String[2];
            String key = gName.substring(gName.indexOf(LEFT_ANGLE_BRACKETS) + 1, gName.indexOf(COMMA)).replaceAll(" ", "");
            String value = gName.substring(gName.indexOf(COMMA) + 1, gName.lastIndexOf(RIGHT_ANGLE_BRACKETS)).replaceAll(" ", "");
            arr[0] = key;
            arr[1] = value;
            return arr;
        }else {
            return new String[0];
        }
    }
    
    public static String processTypeNameForParams(String javaTypeName) {
    	String typeName;
        if (javaTypeName.length() == 1) {
        	typeName =  OBJ_TYPE_NAME;
        } else if (javaTypeName.contains("[")) {
        	typeName = "array("+primitiveClassSimpleName.get(javaTypeName.substring(0, javaTypeName.indexOf('[')))+")";
        } else if (baseClassSimpleName.containsKey(javaTypeName)) {
        	typeName = baseClassSimpleName.get(javaTypeName);
        } else if (primitiveClassSimpleName.containsKey(javaTypeName)) {
        	typeName = primitiveClassSimpleName.get(javaTypeName);
        } else if (javaTypeName.startsWith("java.text")) {
        	typeName = FORMAT_TYPE_NAME;
		} else {
			typeName = OBJ_TYPE_NAME;
		}
        return typeName == null ? OBJ_TYPE_NAME : typeName;
    }

    public static boolean isPrimitive(String type) {
    	if (baseClassSimpleName.containsKey(type)) {
			return true;
		} else if (primitiveClassSimpleName.containsKey(type)) {
			return true;
		} else if (type.startsWith("java.text")) {
			return true;
		} else {
			return false;
		}
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

    /**
     * custom tag
     * @param tagName custom field tag
     * @return boolean
     */
    public static boolean isRequiredTag(String tagName){
        return "required".equals(tagName);
    }

    /**
     * ignore tag request field
     * @param tagName custom field tag
     * @return boolean
     */
    public static boolean isIgnoreTag(String tagName){
        return "ignore".equals(tagName);
    }

    /**
     * ignore param of spring mvc
     * @param paramType param type name
     * @return boolean
     */
    public static boolean isMvcIgnoreParams(String paramType){
    	return "org.springframework.ui.Model".equals(paramType)
                || "org.springframework.ui.ModelMap".equals(paramType)
                || "org.springframework.web.servlet.ModelAndView".equals(paramType)
                || "org.springframework.validation.BindingResult".equals(paramType)
                || "javax.servlet.http.HttpServletRequest".equals(paramType)
                || "javax.servlet.http.HttpServletResponse".equals(paramType);
    }
    
	public static String lowerFirstCapse(String str) {
		char[] chars = str.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
}
