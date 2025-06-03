package com.github.tavi.plague.ecs.spatial.renderable.ik;

import com.badlogic.gdx.math.Vector3;

public record BoneVector(Vector3 original, Vector3 current) {

	public BoneVector(float origX, float origY, float origZ) {
		this(new Vector3(origX, origY, origZ));
	}

	public BoneVector(Vector3 original) {
		this(original, new Vector3(original));
	}
	
}
