package com.github.tavi.plague.ecs.ik;

import com.github.tavi.plague.ecs.spatial.Rotation;
import com.github.tavi.plague.ecs.util.Entity;
import com.github.tavi.plague.engine.archive.Components;
import com.github.tavi.plague.engine.archive.ECSystem;

/**
 * Changes all of the skeleton's bones's {@code Rotation.rotationZ}
 * based on the skeleton look rotation. The bones themselves
 * may not have the {@code LookVector} component, but
 * their skeleton must have one. <br>
 * Placed after {@code EcsLookRotation}.
 * @see com.github.tavi.plague.ecs.spatial.EcsLookRotation
 */
public class EcsSkeletonLooking implements ECSystem {
	private Components components = Components.get();
	private Skeleton skelly = null;
	private Rotation rotation = null;

	@Override
	public boolean validate(int entityId) {
		skelly = components.get(entityId, Skeleton.class);
		rotation = components.get(entityId, Rotation.class);
		return ECSystem.super.areNotNull(skelly, rotation);
	}

	@Override
	public void process(float delta) {
		for (IkArm arm : skelly.arms()) {
			for (Entity entity : arm.entities()) {
				entity.getOr(
						Rotation.class, () -> new Rotation()
						).rotationZ = rotation.rotationZ;
			}
		}
	}

}
