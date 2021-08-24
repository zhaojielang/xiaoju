package com.chetong.doc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class DocUtil {
	
	private static final String DOUBLE_QUOTATION  = "\"";
	
    private static String personName = new String[] {"汤姆","杰瑞","史帕克"}[(int)NumberUtil.getRandomByBetween(0,3)];
    private static String url = "http://www.domain.com";
    private static String email = "tom@mail.com";
    private static String domainName = "www.domain.com";
    private static String phone = "13000000000";
    private static String company = "xxx科技有限公司";
    private static String address = "深圳市福田保税区市花路19号";
    private static String ipv4Address = "127.0.0.1";
    private static String ipv6Address = "fe80::4db1:a249:199:6be%14";
    private static String dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private static String sex = new String[] {"男","女"}[(int)NumberUtil.getRandomByBetween(0,1)];
    private static String idCard = IDCardUtil.getIdCard();
    private static String gender = String.valueOf(NumberUtil.getRandomByBetween(0,1));
    private static String codeFlag = String.valueOf(NumberUtil.getRandomByBetween(0,100));
    private static String messageFlag = new String[] {"success","fail"}[(int)NumberUtil.getRandomByBetween(0,1)];
    private static String jsonAndObj = "{}";
    private static String array = "[]";
    
    private static final Map<String,String> mateFieldValue = new LinkedHashMap<>();
    private static final Map<String,String> randomFieldValue = new HashMap<>();
    static {
    	mateFieldValue.put("resultcode","0000");
    	mateFieldValue.put("resultmsg","成功");
    	mateFieldValue.put("id",String.valueOf(NumberUtil.getRandomByBetween(0,10000)));
        mateFieldValue.put("uuid", UUID.randomUUID().toString());
        mateFieldValue.put("uid",UUID.randomUUID().toString());
        mateFieldValue.put("nickname",personName);
        mateFieldValue.put("name",personName);
        mateFieldValue.put("username",personName);
        mateFieldValue.put("company",company);
        mateFieldValue.put("age",String.valueOf(NumberUtil.getRandomByBetween(0,100)));
        mateFieldValue.put("state",String.valueOf(NumberUtil.getRandomByBetween(0,10)));
        mateFieldValue.put("state",String.valueOf(NumberUtil.getRandomByBetween(0,10)));
        mateFieldValue.put("email",email);
        mateFieldValue.put("address",address);
        mateFieldValue.put("phone",phone);
        mateFieldValue.put("mobile",phone);
        mateFieldValue.put("telephone",phone);
        mateFieldValue.put("ip",ipv4Address);
        mateFieldValue.put("ipv4",ipv4Address);
        mateFieldValue.put("ipv6",ipv6Address);
        mateFieldValue.put("url",url);
        mateFieldValue.put("domain",domainName);
        mateFieldValue.put("birthday", dataTime);
        mateFieldValue.put("code",codeFlag);
        mateFieldValue.put("message",messageFlag);
        mateFieldValue.put("timestamp",dataTime);
        mateFieldValue.put("time",dataTime);
        mateFieldValue.put("date",dataTime);
        mateFieldValue.put("flag",String.valueOf(NumberUtil.getRandomByBetween(0,1)==1));
        mateFieldValue.put("idcard", idCard);
        mateFieldValue.put("sex",sex);
        mateFieldValue.put("gender",gender);
    }
    
    static {
    	randomFieldValue.put("int", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.lang.Integer", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("long", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.lang.Long", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("double", NumberUtil.DECIMAL_FORMAT.format(NumberUtil.getDoubleRandomByLength(6)));
		randomFieldValue.put("java.lang.Double", NumberUtil.DECIMAL_FORMAT.format(NumberUtil.getDoubleRandomByLength(6)));
		randomFieldValue.put("float", NumberUtil.DECIMAL_FORMAT.format(NumberUtil.getDoubleRandomByLength(6)));
		randomFieldValue.put("java.lang.Float", NumberUtil.DECIMAL_FORMAT.format(NumberUtil.getDoubleRandomByLength(6)));
		randomFieldValue.put("short", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.lang.Short", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.math.BigDecimal", NumberUtil.DECIMAL_FORMAT.format(NumberUtil.getDoubleRandomByLength(6)));
		randomFieldValue.put("java.math.BigInteger", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.lang.Boolean", String.valueOf(NumberUtil.getRandomByLength(1)==1));
		randomFieldValue.put("boolean", String.valueOf(NumberUtil.getRandomByLength(1)==1));
		randomFieldValue.put("byte", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("java.lang.Byte", String.valueOf(NumberUtil.getRandomByBetween(0,1000)));
		randomFieldValue.put("char", RandomUtil.createAllRandomCode(4));
		randomFieldValue.put("java.lang.Character", RandomUtil.createAllRandomCode(4));
		randomFieldValue.put("java.lang.String", RandomUtil.createAllRandomCode(4));
		randomFieldValue.put("java.util.Date", dataTime);
		randomFieldValue.put("java.sql.Timestamp", dataTime);
		randomFieldValue.put("java.time.LocalDate", dataTime);
		randomFieldValue.put("java.time.LocalDateTime", dataTime);
		randomFieldValue.put("java.util.Map", jsonAndObj);
		randomFieldValue.put("net.sf.json.JSONObject", jsonAndObj);
		randomFieldValue.put("net.sf.json.JSONArray", array);
		randomFieldValue.put("com.google.gson.JsonObject", jsonAndObj);
		randomFieldValue.put("com.google.gson.JsonArray", array);
		randomFieldValue.put("com.alibaba.fastjson.JSONObject", jsonAndObj);
		randomFieldValue.put("com.alibaba.fastjson.JSONArray", array);
		randomFieldValue.put("java.lang.Object", jsonAndObj);
	}
    
    private static final Set<Entry<String, String>> mateFieldValueSets = mateFieldValue.entrySet();
    
    /**
     * 随机生成json值
     * @param orgType type name
     * @return string
     */
    public static String jsonValueByType(String type){
        String value = randomFieldValue.get(type);
        if (value == null) {
        	value = RandomUtil.createAllRandomCode(4);
		} 
        
        if(DocClassUtil.PRIMITIVE_CLASS_NAME.containsKey(type)){
            return value;
        }else{
            StringBuilder builder = new StringBuilder();
            builder.append(DOUBLE_QUOTATION).append(value).append(DOUBLE_QUOTATION);
            return builder.toString();
        }
    }

    /**
     * 根据字段字段名和type生成字段值
     * 
     * @param type 类型
     * @param filedName 字段名称
     * @return string
     */
    public static String getValByTypeAndFieldName(String type,String filedName){
        String key = filedName.toLowerCase();
        String value = null;
        for(Map.Entry<String,String> entry:mateFieldValueSets){
            if(key.contains(entry.getKey())){
                value = entry.getValue();
                break;
            }
        }
        if(null == value){
            return jsonValueByType(type);
        }else{
            if(DocClassUtil.PRIMITIVE_CLASS_NAME.containsKey(type)){
            	return value;
            }else{
            	StringBuilder builder = new StringBuilder();
                builder.append(DOUBLE_QUOTATION).append(value).append(DOUBLE_QUOTATION);
                return DOUBLE_QUOTATION+value+DOUBLE_QUOTATION;
            }
        }
    }

    /**
     * 是否是合法的java类名称
     * @param className class nem
     * @return boolean
     */
    public static boolean isClassName(String className){
        if(StringUtil.isEmpty(className)){
            return false;
        }
        return !(className.contains("<")&&!className.contains(">")) || !(className.contains(">")&&!className.contains("<"));
    }

    /**
     * match controller package
     * @param packageFilters package filter
     * @param controllerName controller name
     * @return boolean
     */
    public static boolean isMatch(String packageFilters,String controllerName){
        if(StringUtil.isNotEmpty(packageFilters)){
            String[] patterns = packageFilters.split(",");
            for (String str : patterns) {
                if (str.endsWith("*")) {
                    String name = str.substring(0, str.length() - 2);
                    if (controllerName.contains(name)) {
                        return true;
                    }
                } else {
                   if(controllerName.contains(str)){
                       return true;
                   }
                }
            }
        }
        return false;
    }
}
