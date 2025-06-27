package com.github.tavi.plague.ecs.behaviour;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.ECSystem;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.util.vectors.Vectors;

public class LookDirectionSystem implements ECSystem {
	private Components components = Components.get();
	private Transform transform = null;
	private LookDirection lookingAt = null;

	@Override
	public boolean validate(int entityId) {
		lookingAt = components.get(entityId, LookDirection.class);
		transform = components.get(entityId, Transform.class);
		return ECSystem.super.areNotNull(lookingAt, transform);
	}

	@Override
	public void process(float delta) {
		Vector3 original = lookingAt.original();
		Vector3 current = lookingAt.current();
		transform.rotationZ = Vectors.angle(
				original.x, original.y,
				current.x, current.y
				);
	}

}
