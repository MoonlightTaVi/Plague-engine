package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.System;

public class TransformInertionSystem implements System {
	private Components components = Components.get();
	private Vector3 prevVelocity = new Vector3();

	@Override
	public void process(int entityId, float delta) {
		Transform transform = null;
		if ((transform = components.get(entityId, Transform.class)) == null) {
			return;
		}
		prevVelocity.set(transform.velocityVector);
		
		transform.velocityVector
		.set(transform.worldPosition)
		.sub(transform.prevWorldPosition);
		
		transform.accelerationVector
		.set(transform.velocityVector)
		.sub(prevVelocity);
		
		transform.prevWorldPosition.set(transform.worldPosition);
	}

}
