package com.zhb.gradle.thread;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerProducerUsingBlockingQueue {
	private static ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(2);
	
	public static void main(String[] args){
		// create a thread pool with two threads
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ProducerTask());
		executor.execute(new ConsumerTask());
		executor.shutdown();
	}
	
	private static class ProducerTask implements Runnable{
		public void run(){
			try{
				int i = 1;
				while(true){
					System.out.println("Producer writes " + i);
					buffer.put(i++);
					System.out.println(Arrays.toString(buffer.toArray()));
					Thread.sleep((int)(Math.random() * 10000));
				}
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	private static class ConsumerTask implements Runnable{
		public void run(){
			try {
				while(true){
					System.out.println("\t\tConsumer reads " + buffer.take());
					Thread.sleep((int)(Math.random() * 10000));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
