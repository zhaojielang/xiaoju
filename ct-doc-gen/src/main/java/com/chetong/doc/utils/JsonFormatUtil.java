package com.chetong.doc.utils;

public class JsonFormatUtil {

	/**
     * 格式化json字符串
     *
     * @param jsonStr json content
     * @return string
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
        	return "";
        } else {
        	StringBuilder sb = new StringBuilder();
        	char last;
        	char current = '\0';
        	int indent = 0;
        	for (int i = 0; i < jsonStr.length(); i++) {
        		last = current;
        		current = jsonStr.charAt(i);
        		switch (current) {
        		case '{':
        		case '[':
        			sb.append(current);
        			sb.append('\n');
        			indent++;
        			addIndentBlank(sb, indent);
        			break;
        		case '}':
        		case ']':
        			sb.append('\n');
        			indent--;
        			addIndentBlank(sb, indent);
        			sb.append(current);
        			break;
        		case ',':
        			sb.append(current);
        			if (last != '\\') {
        				sb.append('\n');
        				addIndentBlank(sb, indent);
        			}
        			break;
        		default:
        			sb.append(current);
        		}
        	}
        	return sb.toString();
        }
    }

    /**
     * 添加空格
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
