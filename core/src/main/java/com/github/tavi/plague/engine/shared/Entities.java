package com.github.tavi.plague.engine.shared;

import java.util.Collection;

import com.github.tavi.plague.engine.silva.TreeOfEntities;

public interface Entities {
	public static Entities get() {
		return TreeOfEntities.get();
	}
	
	public int createId();
	public boolean idExists(int id);
	public boolean removeId(int id);
	public Collection<Integer> getIds();

}
