package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector2;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.renderable.VisibleSystem;
import com.github.tavi.plague.ecs.spatial.renderable.ik.DirectionalVector;
import com.github.tavi.plague.util.Vectors;

public class RotationSystem implements VisibleSystem {
	private Components components = Components.get();

	@Override
	public void process(int entityId, float delta) {
		Transform transform = null;
		DirectionalVector vector = null;
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(vector = components.get(entityId, DirectionalVector.class)) == null) {
			return;
		}
		// Set rotation based on DirectionalVector
		float rotationY = Vectors.angle(
				new Vector2(vector.original().x, vector.original().z),
				new Vector2(vector.current().x, vector.current().z)
				);
		transform.rotationY = rotationY;
		// Update position
		Transform parent = null;
		if ((parent = transform.parent) == null) {
			return;
		}
		double rad = Math.toRadians(parent.rotationY);
		transform.x = (float) ((transform._x) * Math.cos(rad) - (transform._z) * Math.sin(rad)) - transform._x;
		transform.z = (float) ((transform._x) * Math.sin(rad) + (transform._z) * Math.cos(rad)) - transform._z;
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
