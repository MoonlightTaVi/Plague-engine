package com.github.tavi.plague.shared.components;

public class Transform {

	public float x = 0;
	public float y = 0;
	public float z = 0;

	public float rotationX = 0;
	public float rotationY = 0;
	public float rotationZ = 0;
	
	public Transform parent = null;
	
	
	// ========= TO-DO: Position/rotation relative to parent
	// (Position also should take parent's rotation, as well as its own rotation, into account)
	public float x() {
		return x;
	}
	
	public float y() {
		return x;
	}
	
	public float z() {
		return x;
	}
	
	
	public float rotationX() {
		return rotationX;
	}
	
	public float rotationY() {
		return rotationY;
	}
	
	public float rotationZ() {
		return rotationZ;
	}
	
}
