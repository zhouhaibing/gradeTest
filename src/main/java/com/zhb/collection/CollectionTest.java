package com.zhb.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest {
	public static void main(String[] args){
		
		List<String> lists = new ArrayList<String>();
		lists.add("2.0");
		//String[] arrays = (String[])lists.toArray();
		//System.out.println(arrays);
		
		Double[] dd = lists.toArray(new Double[lists.size()]);
		System.out.println(dd);
	}

}
