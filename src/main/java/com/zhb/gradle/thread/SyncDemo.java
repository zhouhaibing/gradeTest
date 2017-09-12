package com.zhb.gradle.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class SyncDemo {
	public static AtomicInteger count = new AtomicInteger(0);
	public static  void inc() {
		try {
			Thread.sleep(1);// effect obviously
		} catch (InterruptedException e) {
		}
		count.incrementAndGet();
	}
	public static void main(String[] args){
		for(int i=0;i<1000;i++){
			new Thread(new Runnable(){
				public void run(){
					SyncDemo.inc();
				}
			}).start();
		}
		
		
		while(Thread.activeCount() > 1){
			Thread.yield();
		}
		System.out.println(SyncDemo.count);
	}
	


}

class SyncBody extends Thread{
	
	public void run(){
		
	}
}
