package com.zhb.gradle.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentHashMapUtils {
	/**
	 * ConcurrentHashMap 使用场景
	 * 实际上，线程安全的容器，例如map,应用场景并没有想象的那么多。
	 * 在很多情况下，一个业务会涉及到容器的多个操作，即复合操作。
	 * 并发执行时，线程安全的容器只能保证自身的数据不被破坏，但是无法保证业务的行为是否正确。
	 */
	private final Map<String, Long> wordCounts = new ConcurrentHashMap<>();

	public static void main(String[] args) {

	}

	// word count
	/**
	 * single thread is safe,
	 * multiple thread is error.
	 * 
	 * @param key
	 * @return
	 */
	public Long increase(String key) {
		Long num = wordCounts.get(key);
		Long newNum = (num == null) ? 1L : num + 1;
		wordCounts.put(key, newNum);
		return num;
	}

	/**
	 * use putIfAbsent replace in ConcurrentHashMap
	 * ok,but auto boxing and unboxing is mess. 
	 */
	public Long increaseV2(String key) {
		Long oldValue,newValue;
		while (true) {
			oldValue = wordCounts.get(key);
			if (oldValue == null) {
				newValue = 1L;
				if (wordCounts.putIfAbsent(key, newValue) == null)
					break;
			} else {
				newValue = oldValue + 1;
				if (wordCounts.replace(key, oldValue, newValue))
					break;
			}
		}
		return newValue;
	}
	
	/**
	 * use AtomicLong
	 * replace auto boxing and unboxing
	 * AtomicLongMap in google guava
	 */
	private final Map<String,AtomicLong> wordCounts3 = new ConcurrentHashMap<>();
	public Long increaseV3(String key){
		AtomicLong oldValue = wordCounts3.get(key);
		if(oldValue == null){
			AtomicLong newValue = new AtomicLong(0);
			oldValue = wordCounts3.putIfAbsent(key, newValue);
			if(oldValue == null){
				oldValue = newValue;
			}
		}
		return oldValue.incrementAndGet();
	}
	
	
	
	
}
