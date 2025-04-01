package com.github.tavi.plague.sciacallo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import com.github.tavi.plague.shared.Entities;

public class Verberones implements Entities {
	
	private static volatile Entities INSTANCE = null;
	
	public static Entities get() {
		if (INSTANCE == null) {
			synchronized (Verberones.class) {
				if (INSTANCE == null) {
					INSTANCE = new Verberones();
				}
			}
		}
		return INSTANCE;
	}
	
	
	private int count = 0;
	
	private Set<Integer> entities = new HashSet<>();
	private Queue<Integer> disposed = new PriorityQueue<>(50);
	private Map<Object, Integer> tagged = new HashMap<>();

	@Override
	public int create() {
		int id = 0;
		
		if (disposed.isEmpty()) {
			id = ++count;
		} else {
			id = disposed.poll();
		}
		
		entities.add(id);
		return id;
	}

	@Override
	public int create(Object tag) {
		int id = create();
		
		tagged.put(tag, id);
		return id;
	}

	@Override
	public int tagExisting(int id, Object tag) throws NoSuchElementException {
		if (!entities.contains(id)) {
			throw new NoSuchElementException("No entity with ID: " + id);
		}
		tagged.put(tag, id);
		return id;
	}

	@Override
	public int getBy(Object tag) {
		return tagged.get(tag);
	}

	@Override
	public boolean dispose(int id) {
		if (entities.remove(id)) {
			disposed.add(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean dispose(Object tag) {
		Integer id = tagged.get(tag);
		
		if (id != null) {
			return dispose(id);
		}
		
		return false;
	}

	@Override
	public Stream<Integer> stream() {
		return entities.stream();
	}

}
