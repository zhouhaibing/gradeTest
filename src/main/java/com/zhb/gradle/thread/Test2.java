package com.zhb.gradle.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test2 {
	public static void main(String[] args){
		
		Date date = new Date(1477015505291L);
		System.out.println(date);
		
		
		List<String> ss = new ArrayList<String>();
		ss.add("hello");
		ss.add("world");
		ss.add("nice");
		//ss.add(1, "good");
		System.out.println(ss);
		for(String s : ss){
			System.out.println(s);
			//ss.add(2, "hell");;
			ss.remove(0);		}
	}
}

