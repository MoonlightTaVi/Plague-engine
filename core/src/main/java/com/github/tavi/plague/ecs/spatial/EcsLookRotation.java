package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.system.ECSystem;
import com.github.tavi.plague.util.vectors.Vectors;

/**
 * Changes {@code Rotation}'s {@code rotationZ} value,
 * based on Entity's LookVector. <br>
 * NOTE: This system adds Rotation component
 * automatically, if LookVector is present.
 * @see com.github.tavi.plague.ecs.spatial.Rotation
 * @see com.github.tavi.plague.ecs.spatial.LookVector
 */
public class EcsLookRotation implements ECSystem {
	private Components components = Components.get();
	private Rotation rotation = null;
	private LookVector lookingAt = null;

	@Override
	public boolean validate(int entityId) {
		lookingAt = components.get(entityId, LookVector.class);
		rotation = components.getOr(entityId, Rotation.class, () -> new Rotation());
		return ECSystem.super.areNotNull(lookingAt, rotation);
	}

	@Override
	public void process(float delta) {
		Vector3 original = lookingAt.original();
		Vector3 current = lookingAt.current();
		rotation.rotationZ = Vectors.angle(
				original.x, original.y,
				current.x, current.y
				);
	}

}
