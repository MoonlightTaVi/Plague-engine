package com.github.tavi.plague.ecs.spatial.renderable.fabrik.components;

import com.badlogic.gdx.math.Vector3;

public record DirectionalVector(Vector3 original, Vector3 current) {

	public DirectionalVector(float origX, float origY, float origZ) {
		this(new Vector3(origX, origY, origZ));
	}

	public DirectionalVector(Vector3 original) {
		this(original, new Vector3(original));
	}
	
}
