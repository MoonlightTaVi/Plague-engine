package com.github.tavi.plague.engine.archive;

import java.util.stream.Stream;

@Deprecated
public interface Entities {
	
	public static Entities get() {
		return SciacalloEntities.get();
	}
	
	public int create();
	public int createIfAbsent(int id);
	public boolean dispose(int id);
	public Stream<Integer> stream();
	
}
