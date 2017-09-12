package com.zhb.test.classAndClass;

public class BClass extends AClass{
	
	public void sayHi(){
		System.out.println("i am sub b");
	}
	
	public static void main(String[] args){
		AClass a = new AClass();
		a.sayHi();
	}

}
