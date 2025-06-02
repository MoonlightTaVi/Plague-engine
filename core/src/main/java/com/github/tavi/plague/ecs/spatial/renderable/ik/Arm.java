package com.github.tavi.plague.ecs.spatial.renderable.ik;

public class Arm {
	
	public int size = 0;
	public int[] IDs;
	
	public Arm(int length) {
		IDs = new int[length];
	}
	
	public int push(int id) throws ArrayIndexOutOfBoundsException {
		try {
			IDs[size++] = id;
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Arm is already set up, cannot add any more bones. Entity ID: " + id);
		}
		return id;
	}
	
}
