package com.zhb.test.classAndClass;

public class AClass {
	public static final String name = "superClass";
	public void sayHi(){
		System.out.println("i am super a");
	}
	
	public void superCall(){
		System.out.println("super call haha");
		this.sayHi();
	}

}
