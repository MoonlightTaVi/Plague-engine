package com.github.tavi.plague.ecs.behaviour;

import com.badlogic.gdx.math.Vector3;

public record LookDirection(Vector3 original, Vector3 current) {

	public LookDirection(Vector3 original) {
		this(new Vector3(original), new Vector3(original));
	}

	public LookDirection(float x, float y, float z) {
		this(new Vector3(x, y, z));
	}
	
}
