package com.zhb.gradle.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
	//public volatile static int count = 0;

	 public static AtomicInteger count = new AtomicInteger(0);//update version

	public static void inc() {
		try {
			Thread.sleep(1);// effect obviously
		} catch (InterruptedException e) {
		}
		//count++;
		count.incrementAndGet();
	}
	
	public static AtomicInteger numm = new AtomicInteger(0);
	public int getNumm(){
		return numm.get();
	}
	public synchronized void addd(){
		numm.incrementAndGet();
	}
	
	
	
	
	public static int num = 0;
	public synchronized void add(){// don't think that only add synchronized is ok.
		/*try{
			Thread.sleep(1);
		}catch(InterruptedException e){
		}*/
		num++;
		
		/*System.out.println("开始");
		try{
			Thread.sleep(100);
		}catch(InterruptedException e){
		}
		System.out.println("结束");*/
	}

	public static void main(String[] args) throws InterruptedException {

		/*ExecutorService service=Executors.newFixedThreadPool(Integer.MAX_VALUE);
		
		for (int i = 0; i < 1000; i++) {
		    service.execute(new Runnable() {
		        @Override
		        public void run() {
		            VolatileTest.inc();
		        }
		    });
		}
		 
		service.shutdown();
		//给予一个关闭时间（timeout），但是实际关闭时间应该会这个小
		service.awaitTermination(300,TimeUnit.SECONDS);
		System.out.println(VolatileTest.count);*/

		// test volatile + num++  error
		/*for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					VolatileTest.inc();
				}

			}).start();
		}
		System.out.println("运行结果:Counter.count=" + VolatileTest.count);*/
		
		//test synchronized + num++ right
		/*VolatileTest tt = new VolatileTest();
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					tt.add();
				}

			}).start();
		}
		System.out.println(VolatileTest.num);*/
		
		
		/*VolatileTest tt2 = new VolatileTest();
		for (int i = 0; i < 5; i++) {
			myThread mt = new myThread(tt2);
			mt.start();
		}
		System.out.println(VolatileTest.num);*/
		
		/*for (int i = 0; i < 5; i++) {
			VolatileTest tt2 = new VolatileTest();
			myThread mt = new myThread(tt2);
			mt.start();
		}
		System.out.println(VolatileTest.num);*/
		
		
		VolatileTest tt2 = new VolatileTest();
		for (int i = 0; i < 100; i++) {
			myThread mt = new myThread(tt2);
			mt.start();
		}
		System.out.println(tt2.getNumm());
	}
	
	public static class myThread extends Thread{
		private VolatileTest vt;
		public myThread(VolatileTest vt){
			this.vt = vt;
		}
		public void run(){
			vt.addd();
		}
	}
}
