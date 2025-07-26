package com.github.tavi.plague.engine.entity;

import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

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
	
	
	private TreeSet<Integer> entities = new TreeSet<>();

	@Override
	public int createId() {
		int newId = 1;
		if (!entities.isEmpty()) {
			newId += entities.last();
		}
		entities.add(newId);
		return newId;
	}

	@Override
	public boolean idExists(int id) {
		return entities.contains(id);
	}

	@Override
	public boolean removeId(int id) {
		return entities.contains(id);
	}

	@Override
	public Collection<Integer> getIds() {
		return entities;
	}
	
}
