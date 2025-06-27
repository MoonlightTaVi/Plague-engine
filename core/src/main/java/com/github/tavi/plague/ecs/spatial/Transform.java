package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;

public class Transform {
	public Vector3 worldPosition = new Vector3();
	public Vector3 originalLocalPosition = new Vector3();
	public Vector3 rotatedLocalPosition = new Vector3();

	public Vector3 prevWorldPosition = new Vector3();
	public Vector3 velocityVector = new Vector3();
	public Vector3 accelerationVector = new Vector3();
	
	public float rotationX = 0;
	public float rotationY = 0;
	/** Looking direction. */
	public float rotationZ = 0;
	
	public Transform parent = null;
	
	public Transform() { }
	
	public Transform(float offsetX, float offsetY, float offsetZ) {
		originalLocalPosition.set(offsetX, offsetY, offsetZ);
	}
	

	
	public void setWorldPosition(Vector3 fromVector) {
		worldPosition.set(fromVector);
	}
	
	public void setWorldPosition(float x, float y, float z) {
		worldPosition.set(x, y, z);
	}
	
	
	
}
