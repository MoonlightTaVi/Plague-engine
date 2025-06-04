package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;

public class Transform {
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float _x = 0;
	public float _y = 0;
	public float _z = 0;

	public float rotationX = 0;
	public float rotationY = 0;
	public float rotationZ = 0;
	
	public Transform parent = null;
	
	public Transform() { }
	
	public Transform(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}

	
	public void set(Vector3 fromVector) {
		x = fromVector.x;
		y = fromVector.y;
		z = fromVector.z;
	}
	
	public float x() {
		return _x + x + (parent != null ? parent.x : 0);
	}
	
	public float y() {
		return _y + y + (parent != null ? parent.y : 0);
	}
	
	public float z() {
		return _z + z + (parent != null ? parent.z : 0);
	}
	
}
