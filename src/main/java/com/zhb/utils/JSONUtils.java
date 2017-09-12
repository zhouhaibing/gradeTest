package com.zhb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {

	public static void main(String[] args) {
		String s = "{\"hello\":\"world\",\"nice\":\"good\"}";
		String ss = "{\"hello\":\"worl\",\"great\":\"good\"}";

		JSONObject ssObj = JSONObject.parseObject(ss);
		JSONObject sObj = JSONObject.parseObject(s);
		ssObj.putAll(toMap(sObj));
		System.out.println(ssObj.toJSONString());
	}

	// JSONObject to map
	public static Map<String, Object> toMap(JSONObject object) {
		Map<String, Object> resultMap = new HashMap<>();
		if (object == null) {
			return resultMap;
		}
		Iterator<String> keysIter = object.keySet().iterator();
		while (keysIter.hasNext()) {
			String key = keysIter.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			resultMap.put(key, value);
		}
		return resultMap;
	}

	// JSONArray to list
	public static List<Object> toList(JSONArray array) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	// object to JSONObject
	public static JSONObject toJSONObject(Object object) {
		String objStr = JSON.toJSONString(object);
		return JSON.parseObject(objStr);
	}

	// object to map
	public static Map<String, Object> toMap(Object object) {
		return toMap(toJSONObject(object));
	}

}
