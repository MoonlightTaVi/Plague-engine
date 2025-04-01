package com.github.tavi.plague.shared;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.github.tavi.plague.sciacallo.Verberones;

public interface Entities {
	
	public static Entities get() {
		return Verberones.get();
	}
	
	public int create();
	public int create(Object tag);
	public int tagExisting(int id, Object tag) throws NoSuchElementException;
	public int getBy(Object tag);
	
	public boolean dispose(int id);
	public boolean dispose(Object tag);
	
	public Stream<Integer> stream();
	
}
