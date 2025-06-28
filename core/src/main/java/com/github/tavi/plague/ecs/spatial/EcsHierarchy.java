package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.ECSystem;
import com.github.tavi.plague.ecs.util.ParentRef;

/**
 * Updates Entity's {@code Transform} and {@code Rotation}
 * components based on its {@code ParentRef}
 * (ID of the parent to inherit from). <br>
 * NOTE: This system adds Rotation component
 * automatically, if both Transform and ParentRef
 * components are present.
 * @see com.github.tavi.plague.ecs.util.ParentRef
 * @see com.github.tavi.plague.ecs.spatial.Transform
 * @see com.github.tavi.plague.ecs.spatial.Rotation
 */
public class EcsHierarchy implements ECSystem {
	private final Vector3 lookingAxis = new Vector3(0, 0, 1);
	private final Vector3 rotationAxis = new Vector3(0, -1, 0);
	private Components components = Components.get();

	private ParentRef parent = null;
	private Transform transform = null;
	private Transform parentTransform = null;
	private Rotation rotation = null;
	private Rotation parentRotation = null;

	@Override
	public void process(float delta) {
		/* 
		 * rotationZ by default is 0, but if parent has a LookVector
		 * component, and the EcsLookRotation is active,
		 * all children in the chain will inherit this angle one by one.
		 */
		rotation.rotationZ = parentRotation.rotationZ;
		
		// Rotate around parent with parent
		transform.rotatedOffset.set(
				transform.originalOffset
				)
		.rotate(rotationAxis, parentRotation.rotationY)
		.rotate(lookingAxis, parentRotation.rotationZ)
		;
		
		// Update world position
		transform.worldPosition.set(
				transform.rotatedOffset
				).add(parentTransform.worldPosition);
	}

	@Override
	public boolean validate(int entityId) {
		parent = components.get(entityId, ParentRef.class);
		transform = components.get(entityId, Transform.class);
		rotation = components.getOr(entityId, Rotation.class, () -> new Rotation());
		return ECSystem.super.areNotNull(parent, transform, rotation) && postValidate();
	}
	
	private boolean postValidate() {
		int parentId = parent.parentId();
		parentTransform = components.get(parentId, Transform.class);
		parentRotation = components.get(parentId, Rotation.class);
		return ECSystem.super.areNotNull(parentTransform, parentRotation);
	}

}
