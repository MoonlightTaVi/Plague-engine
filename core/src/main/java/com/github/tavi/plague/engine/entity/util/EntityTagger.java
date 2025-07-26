package com.github.tavi.plague.engine.entity.util;

import java.util.NoSuchElementException;

public interface EntityTagger {
	public int create(Object tag);
	public int tagExisting(int id, Object tag) throws NoSuchElementException;
	public int getBy(Object tag);
	public boolean dispose(Object tag);
}
