package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.renderable.VisibleSystem;

public class VisibleHierarchySystem implements VisibleSystem {
	private final Vector3 rotationAxis = new Vector3(0, -1, 0);
	private Components components = Components.get();

	@Override
	public void process(int entityId, float delta) {
		Transform transform = null;
		Transform parent = null;
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(parent = transform.parent) == null) {
			return;
		}
		// Rotate around parent with parent
		transform.rotatedLocalPosition.set(
				transform.originalLocalPosition
				).rotate(rotationAxis, parent.rotationY);
		// Update world position
		transform.worldPosition.set(
				transform.rotatedLocalPosition
				).add(parent.worldPosition);
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
