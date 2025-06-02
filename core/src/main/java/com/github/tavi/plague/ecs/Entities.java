package com.github.tavi.plague.ecs;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public interface Entities {
	
	public static Entities get() {
		return SciacalloEntities.get();
	}
	
	public int create();
	public int create(Object tag);
	public int tagExisting(int id, Object tag) throws NoSuchElementException;
	public int getBy(Object tag);
	
	public boolean dispose(int id);
	public boolean dispose(Object tag);
	
	public Stream<Integer> stream();
	
}
