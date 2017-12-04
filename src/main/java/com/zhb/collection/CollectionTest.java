package com.zhb.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionTest {
	public static void main(String[] args){
		
		/*List<String> lists = new ArrayList<String>();
		lists.add("2.0");
		//String[] arrays = (String[])lists.toArray();
		//System.out.println(arrays);
		
		Double[] dd = lists.toArray(new Double[lists.size()]);
		System.out.println(dd);*/
		
		
		//concurrentHashMap
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
		map.put("hello", 22);
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("hello",null);
		System.out.println(hashMap.get("hello"));
		
	}

}
