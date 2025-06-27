package com.github.tavi.plague.ecs.spatial.renderable.fabrik;

import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.ECSystem;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.*;

public class SkeletonLookingSystem implements ECSystem {
	private Components components = Components.get();
	private Skelly skelly = null;
	private Transform transform = null;

	@Override
	public boolean validate(int entityId) {
		skelly = components.get(entityId, Skelly.class);
		transform = components.get(entityId, Transform.class);
		return ECSystem.super.areNotNull(skelly, transform);
	}

	@Override
	public void process(float delta) {
		for (ArmType arm : skelly.arms()) {
			for (int boneId : skelly.get(arm).IDs) {
				components.getOr(
						boneId,
						Transform.class, () -> new Transform()
						).rotationZ = transform.rotationZ;
			}
		}
	}

}
