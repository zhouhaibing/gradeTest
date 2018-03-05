package com.zhb.designPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * JDK 动态代理
 * 1. 基本服务
 * 2. 代理类
 *  依赖基本服务实体
 *  Proxy.newProxyInstance(classloader, interfaces, invocationHandler{invoke()})
 * @author Administrator
 *
 */
public class DynamicProxy {
	public static void main(String[] args){
		Bird bird = new BigBird();
		BigBirdProxyHandler birdProxyHandler = new BigBirdProxyHandler(bird);
		Bird birdProxy = (Bird)birdProxyHandler.getProxy();
		birdProxy.sing("big big world");
	}

}

interface Bird{
	public abstract String sing(String str); 
}

class BigBird implements Bird{

	@Override
	public String sing(String str) {
		System.out.println("sing " + str);
		return "sing " + str;
	}
}

class BigBirdProxyHandler implements InvocationHandler{
	private Object bird;
	public  BigBirdProxyHandler(Object obj) {
		this.bird = obj;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("start singing");
	    System.out.println(proxy.getClass());
		//Object retObj = method.invoke(bird, args);
		Object retObj = method.invoke(bird, args);
		System.out.println("stop singing");
		return retObj;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(BigBirdProxyHandler.class.getClassLoader(), bird.getClass().getInterfaces(), this);
	}
	
}