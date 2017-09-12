package com.zhb.db.test;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class Hello {
	public static void main(String[] args) {
		System.out.println(getNumOfUnlockScreen(2,9));
		
		
		
	}

	public static <T> T ParseRequestParameter(HttpServletRequest request, Class<?> clazz) {
		JSONObject object = new JSONObject();
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String nextElement = names.nextElement();
			object.put(nextElement, request.getParameter(nextElement));
		}
		T result = (T) JSONObject.parseObject(object.toJSONString(), clazz);
		return result;
	}
	
	/**
	 * this is error
	 * pointNum 构成解锁图案的点数
	 * sum 解锁屏幕总共的点数
	 * @param pointNum 
	 * @param sum
	 * @return
	 */
	public static int getNumOfUnlockScreen(int pointNum, int sum) {
		int result = 0;
		for(int i = pointNum; i <= sum ; i++) {
			result += combinationC(i,sum);
		}
		return result;
	}
	
	/**
	 * 使用图的深度搜索算法
	 * @param n
	 * @return
	 */
	public static int UnlockScreen(int pointNum, int sum) {
		
		return 0;
	}
	
	public static int factorial(int n){
		int result =1;
		for(int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
	
	public static int arrangeA(int n,int m){
		return factorial(m) / factorial(m - n);
	}
	
	public static int combinationC(int n,int m){
		return arrangeA(n, m) /  factorial(n);
	}
	

}
