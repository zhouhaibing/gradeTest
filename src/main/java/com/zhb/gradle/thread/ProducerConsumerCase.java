package com.zhb.gradle.thread;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author ZHOUHAIBING
 *         design a simple producer/consumer model
 */
public class ProducerConsumerCase {
	public static Buffer buffer = new Buffer();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ProducerTask(buffer));
		executor.execute(new ConsumerTask(buffer));
		executor.shutdown();
	}

}

class ConsumerTask implements Runnable {
	private Buffer buffer;
	public ConsumerTask(Buffer buffer){
		this.buffer = buffer;
	}
	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("\t\t\tConsumer reads " + buffer.read());
				// Put the thread to sleep
				Thread.sleep((int) (Math.random() * 10000));
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

}

class ProducerTask implements Runnable {
	private Buffer buffer;
	public ProducerTask(Buffer buffer){
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		try{
			int i = 1;
			while(true){
				System.out.println("write a value:" + i);
				buffer.write(i++);
				// Put the thread to sleep
				Thread.sleep((int) (Math.random() * 10000));
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}

class Buffer {
	private static final int INIT_CAPACITY = 1;
	private LinkedList<Integer> queue;

	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();

	public Buffer() {
		queue = new LinkedList<Integer>();
	}

	// write
	public void write(int value) {
		lock.lock();
		try {
			while (queue.size() == INIT_CAPACITY) {
				System.out.println("Wait for notFull condition");
				notFull.await();
			}
			queue.offer(value);
			notEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	// read
	public int read() {
		int value = 0;
		lock.lock();
		try {
			while (queue.size() == 0) {
				System.out.println("buffer is empty,wait for notEmpty condition.");
				notEmpty.await();
			}
			value = queue.remove();
			notFull.signal();
			// return value is permitted too.
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			return value;
		}
	}

}