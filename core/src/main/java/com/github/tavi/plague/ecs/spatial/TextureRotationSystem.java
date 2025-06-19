package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector2;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.ECSystem;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.DirectionalVector;
import com.github.tavi.plague.util.vectors.Vectors;

public class TextureRotationSystem implements ECSystem {
	private Components components = Components.get();
	
	private Transform transform = null;
	private DirectionalVector vector = null;

	@Override
	public void process(float delta) {
		// Set rotation based on DirectionalVector
		float rotationY = Vectors.angle(
				new Vector2(vector.original().x, vector.original().z),
				new Vector2(vector.current().x, vector.current().z)
				);
		transform.rotationY = rotationY;
	}

	@Override
	public boolean validate(int entityId) {
		transform = components.get(entityId, Transform.class);
		vector = components.get(entityId, DirectionalVector.class);
		return ECSystem.super.areNotNull(transform, vector);
	}

}
