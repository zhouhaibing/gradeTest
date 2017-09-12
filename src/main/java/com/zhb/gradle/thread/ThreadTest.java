package com.zhb.gradle.thread;

public class ThreadTest extends Thread{
	
	@Override
	public void run() {
		System.out.println("hello world");
	}
	
	public static void main(String[] args){
		ThreadTest tt = new ThreadTest();
		tt.start();
		
	}
	

}

/*public class ThreadTest implements Runnable{
	
	
	
	
	
	
	public static void main(String[] args){
		ThreadTest tt = new ThreadTest();
		tt.run();
	}

	@Override
	public void run() {
		System.out.println("hello world");
	}
	

}*/

class Hello{
	public Hello(){
		
	}
	
	public synchronized void  sayHello(){
		System.out.println("开始");
		try {  
            Thread.sleep(1000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
		System.out.println("结束");
	}
}
