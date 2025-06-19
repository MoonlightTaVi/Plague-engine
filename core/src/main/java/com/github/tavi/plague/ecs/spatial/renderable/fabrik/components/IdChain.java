package com.github.tavi.plague.ecs.spatial.renderable.fabrik.components;

public class IdChain {
	
	public int size = 0;
	public int[] IDs;
	
	public IdChain(int length) {
		IDs = new int[length];
	}
	
	public int push(int id) {
		IDs[size] = id;
		size++;
		return id;
	}
	
}
