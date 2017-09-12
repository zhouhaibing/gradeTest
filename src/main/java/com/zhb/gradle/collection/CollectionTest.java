package com.zhb.gradle.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionTest {
	public static void main(String[] args){
		Map<String,String> map = new HashMap<>();
		map.put("hello", "world");
		map.put("good", "job");
		for(Map.Entry<String, String> entry : map.entrySet()){
			System.out.println("name:" + entry.getKey() + " value:" + entry.getValue());
			if(entry.getKey().equals("hello")){
				entry.setValue("perfect");
			}
		}
		System.out.println(map);
		
		for(Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); iter.hasNext();){
			Map.Entry<String, String> entry = iter.next();
			System.out.println("name:" + entry.getKey() + " value:" + entry.getValue());
		}
		
		
		if(map instanceof Map<?,?>){
			Map<String,String> mapp = (Map<String,String>)map;
		}
		if(map instanceof Map){//preferred way
			Map<String,String> mapp = (Map<String,String>)map;
		}
		
		//set
		//Set<?> set = new HashSet<?>(); error
	}

}
