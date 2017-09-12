package com.zhb.db.test;

public class JDBCTest {
	
	public JDBCTest(){
		
	}
	public static void main(String[] args){
		try {
			Class<?> czz = Class.forName("com.zhb.db.test.DBTest");
			Object o =  czz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(0>>1);
	}

}
