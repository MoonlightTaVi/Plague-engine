package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.ECSystem;

public class VisibleHierarchySystem implements ECSystem {
	private final Vector3 lookingAxis = new Vector3(0, 0, 1);
	private final Vector3 rotationAxis = new Vector3(0, -1, 0);
	private Components components = Components.get();
	
	private Transform transform = null;
	private Transform parent = null;

	@Override
	public void process(float delta) {
		/* 
		 * rotationZ by default is 0, but if parent has a LookDirection
		 * component, and the LookDirectionSystem is active,
		 * all children in the chain will inherit this angle one by one.
		 */
		transform.rotationZ = parent.rotationZ;
		
		// Rotate around parent with parent
		transform.rotatedLocalPosition.set(
				transform.originalLocalPosition
				)
		.rotate(rotationAxis, parent.rotationY)
		.rotate(lookingAxis, transform.rotationZ)
		;
		// Update world position
		transform.worldPosition.set(
				transform.rotatedLocalPosition
				).add(parent.worldPosition);
	}

	@Override
	public boolean validate(int entityId) {
		transform = components.get(entityId, Transform.class);
		parent = transform != null ? transform.parent : null;
		return ECSystem.super.areNotNull(transform, parent);
	}

}
