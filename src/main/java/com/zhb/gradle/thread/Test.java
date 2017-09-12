package com.zhb.gradle.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.zhb.test.http.HttpUtil;
import com.zhb.test.http.HttpUtils;

public class Test {
	/*public volatile int inc = 0;
	public void increase() {
	    inc++;
	}
	 
	public static void main(String[] args) {
	    final Test test = new Test();
	    for(int i=0;i<10;i++){
	        new Thread(){
	            public void run() {
	                for(int j=0;j<1000;j++)
	                    test.increase();
	            };
	        }.start();
	    }
	     
	    while(Thread.activeCount()>1)  //保证前面的线程都执行完
	        Thread.yield();
	    System.out.println(test.inc);
	}*/

	private static boolean stopRequested;

	private static synchronized void requestStop() {
		stopRequested = true;
	}

	private static synchronized boolean stopRequested() {
		return stopRequested;
	}

	public static void main1(String[] args) throws InterruptedException {
		Thread backgroundThread = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				// while (!stopRequested)
				// i++;

				while (!stopRequested())
					i++;
			}
		});
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		stopRequested = true;
	}
	
	public static void main(String[] args){
		String[] urls = new String[]{"http://www.baidu.com","http://www.xgsdk.com","http://www.hhh.com"};
		concurrentfetchUrl(urls);
		
		//System.out.println(HttpUtil.pingURL(urls[0]));
	}
	
	public static void concurrentfetchUrl(String[] urls){
		if(urls == null || urls.length ==0){
			return;
		}
		CountDownLatch latch = new CountDownLatch(urls.length);
		long startTime = System.currentTimeMillis();
		for(int i=0;i<urls.length;i++){
			final int tempi = i;
			Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					HttpUtil.pingURL(urls[tempi]);
					System.out.println("http get " + urls[tempi] + ",time cost " + (System.currentTimeMillis() - time));
					latch.countDown();
				}
				
			});
			t.start();
		}
		
		try {
			latch.await();
			System.out.println("total time is " + (System.currentTimeMillis() - startTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

