package com.chetong.doc.utils;

public class JsonFormatUtil {
	
	private static final char NEW_LINE = '\n';
	private static final char TAB = '\t';
	private static final char COMMA = ',';
	private static final char LEFT_BRACKETS = '[';
	private static final char RIGHT_BRACKETS = ']';
	private static final char BACK_SLASH  = '\\';
	private static final char LEFT_CURLY_BRACKETS = '{';
	private static final char RIGHT_CURLY_BRACKETS = '}';
	
	private JsonFormatUtil() {}
	
	/**
     * 格式化json字符串
     *
     * @param jsonStr json content
     * @return string
     */
    public static String formatJson(StringBuilder jsonStr) {
    	StringBuilder sb = new StringBuilder();
    	char last;
    	char current = '\0';
    	int indent = 0;
    	int[] jsonArrs = jsonStr.chars().toArray();
    	jsonStr.reverse();
    	for (int i = 0; i < jsonArrs.length; i++) {
    		last = current;
    		current = (char)jsonArrs[i];
    		switch (current) {
    		case LEFT_BRACKETS:
    		case LEFT_CURLY_BRACKETS:
    			sb.append(current);
    			sb.append(NEW_LINE);
    			indent++;
    			addIndentBlank(sb, indent);
    			break;
    		case RIGHT_BRACKETS:
    		case RIGHT_CURLY_BRACKETS:
    			sb.append(NEW_LINE);
    			indent--;
    			addIndentBlank(sb, indent);
    			sb.append(current);
    			break;
    		case COMMA:
    			sb.append(current);
    			if (last != BACK_SLASH) {
    				sb.append(NEW_LINE);
    				addIndentBlank(sb, indent);
    			}
    			break;
    		default:
    			sb.append(current);
    		}
    	}
    	return sb.toString();
    }

    /**
     * 添加空格
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append(TAB);
        }
    }
}
