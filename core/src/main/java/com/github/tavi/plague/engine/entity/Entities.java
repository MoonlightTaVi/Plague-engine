package com.github.tavi.plague.engine.entity;

import java.util.Collection;

public interface Entities {
	public static Entities get() {
		return TreeOfEntities.get();
	}
	
	public int createId();
	public boolean idExists(int id);
	public boolean removeId(int id);
	public Collection<Integer> getIds();

}
