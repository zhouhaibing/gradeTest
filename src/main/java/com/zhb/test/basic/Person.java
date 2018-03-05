package com.zhb.test.basic;

import com.alibaba.fastjson.JSON;

public class Person {
	public String name;
	public int age;
	public Person(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}
