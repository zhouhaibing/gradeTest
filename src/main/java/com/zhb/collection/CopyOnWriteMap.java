package com.zhb.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CopyOnWriteMap<K,V> implements Map<K,V>,Cloneable {
	private transient volatile Map<K,V> internalMap;
	
	public CopyOnWriteMap() {
		internalMap = new ConcurrentHashMap<K,V>();
	}
	
	public Map<K,V> getMap(){
		return internalMap;
	}
	
	public void setMap(Map<K,V> map){
		this.internalMap = map;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		return internalMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		synchronized (this) {
			Map<K,V> newMap = new ConcurrentHashMap<>(getMap());
			newMap.put(key, value);
			setMap(newMap);
			return value;
		}
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized (this) {
			Map<K,V> newMap = new ConcurrentHashMap<>(getMap());
			newMap.putAll(m);
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
