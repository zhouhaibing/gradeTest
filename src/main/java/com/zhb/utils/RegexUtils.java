package com.zhb.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则表达式
 * @author ZHOUHAIBING
 *
 */
public class RegexUtils {

private static Map<String, String> replaceMap = new HashMap<String, String>();// hello -> world
	
	// XG_hello_XG --> world
	private static String replaceByRegPattern(String content, String regPattern) {
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            StringBuffer sb = new StringBuffer();
            do {
                String tokenKey = matcher.group(1);
                String tokenValue = replaceMap.get(tokenKey);
                if (null == tokenValue || "".equals(tokenValue.trim())) {
                    //throw new StopException("未找到键: " + tokenKey + " 请前往西瓜 Web控制台检查高级配置.");
                }
                
                String replacement = Matcher.quoteReplacement(tokenValue);
                matcher.appendReplacement(sb, replacement);
            } while (matcher.find());
            matcher.appendTail(sb);
            content = sb.toString();
        }
        return content;
    }
	// XG_hello_XG --> XG_world_XG
	private static String replaceOnlyValue(String content, String regPattern) {
		Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            StringBuffer sb = new StringBuffer();
            do {
            	String allValue = matcher.group();
            	
                String tokenKey = matcher.group(1);
                String tokenValue = replaceMap.get(tokenKey);
                if (null == tokenValue || "".equals(tokenValue.trim())) {
                    //throw new StopException("未找到键: " + tokenKey + " 请前往西瓜 Web控制台检查高级配置.");
                }
                String replacement = Matcher.quoteReplacement(tokenValue);
                replacement = allValue.replace(tokenKey, replacement);
                matcher.appendReplacement(sb, replacement);
            } while (matcher.find());
            matcher.appendTail(sb);
            content = sb.toString();
        }
        return content;
	}
}
