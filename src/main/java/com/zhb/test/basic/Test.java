package com.zhb.test.basic;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;


public class Test {
	/*public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		for(int i=0;i<n;i++){
			int m = input.nextInt();
			int q = input.nextInt();
			int[] nums = new int[m];
			for(int j=0;j<m;j++){
				nums[j] = input.nextInt();
			}
			for(int k=0;k<q;k++){
				int searchI = input.nextInt();
				System.out.println(getIndexInArray(nums,searchI));
			}
		}
	}*/
	
	public static void main(String[] args){
		String ss = "{\"name\":\"haha\",\"age\":12,\"good\":\"xxx\"}";
		
		Person p = JSONObject.parseObject(ss, Person.class);
		System.out.println(p);
		
	}
	
	public static int getIndexInArray(int[] array,int key){
		for(int i=0;i<array.length;i++){
			if(array[i] == key){
				return i;
			}
		}
		return -1;
	}
	
	
	
	

}
