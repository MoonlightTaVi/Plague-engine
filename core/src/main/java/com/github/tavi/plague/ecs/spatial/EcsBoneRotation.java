package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector2;
import com.github.tavi.plague.ecs.ik.BoneVector;
import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.system.ECSystem;
import com.github.tavi.plague.util.vectors.Vectors;

/**
 * Updates Entity's {@code Rotation.rotationY} (which represents
 * a Texture's on-screen rotation) from this Entity's {@code BoneVector}
 * components. Used along with Inverse Kinematics algorithms.
 * @see com.github.tavi.plague.ecs.spatial.Rotation
 * @see com.github.tavi.plague.ecs.ik.BoneVector
 */
public class EcsBoneRotation implements ECSystem {
	private Components components = Components.get();
	
	private Rotation rotation = null;
	private BoneVector vector = null;

	@Override
	public boolean validate(int entityId) {
		rotation = components.get(entityId, Rotation.class);
		vector = components.get(entityId, BoneVector.class);
		return ECSystem.super.areNotNull(rotation, vector);
	}

	@Override
	public void process(float delta) {
		// Set rotation based on BoneVector
		float rotationY = Vectors.angle(
				new Vector2(vector.original().x, vector.original().z),
				new Vector2(vector.current().x, vector.current().z)
				);
		rotation.rotationY = rotationY;
	}

}
