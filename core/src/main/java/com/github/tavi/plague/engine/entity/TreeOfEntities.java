package com.github.tavi.plague.engine.entity;

import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class TreeOfEntities implements Entities {
	private static TreeOfEntities instance = null;
	private static ReentrantLock lock = new ReentrantLock();
	public static TreeOfEntities get() {
		try {
			lock.lock();
			if (instance == null) {
				instance = new TreeOfEntities();
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}
	
	
	private TreeMap<Integer, Integer> entities = new TreeMap<>();

	@Override
	public int create() {
		int newId = entities.lastKey() + 1;
		entities.put(newId, 0);
		return newId;
	}

	@Override
	public int createIfAbsent(int id) {
		if (!entities.containsKey(id)) {
			entities.put(id, 0);
		}
		return id;
	}

	@Override
	public boolean dispose(int id) {
		boolean success = false;
		if (entities.containsKey(id)) {
			success = true;
			entities.remove(id);
		}
		return success;
	}

	@Override
	public Stream<Integer> stream() {
		return entities.keySet().stream();
	}
	
	public int getMask(int id) {
		return entities.getOrDefault(id, 0);
	}
	
	public int setMask(int id, int mask) {
		return entities.put(id, mask);
	}
	
	
}
