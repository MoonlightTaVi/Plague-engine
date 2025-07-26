package com.github.tavi.plague.engine.entity;

import java.util.stream.Stream;

public interface Entities {
	
	public static Entities get() {
		return SciacalloEntities.get();
	}
	
	public int create();
	public int createIfAbsent(int id);
	public boolean dispose(int id);
	public Stream<Integer> stream();
	
}
