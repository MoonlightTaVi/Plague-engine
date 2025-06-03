package com.github.tavi.plague.ecs.spatial.renderable.ik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.renderable.ik.Arm.Type;

public class HumanMovementStrategy implements LimbMovementStrategy {

	/**
	 * PLACEHOLDER 
	 * @return World mouse cursor position for spine, null otherwise
	 */
	@Override
	public Vector3 calculateTargetPosition(
			Vector3 baseOrigin,
			MovementState movementState,
			Type armType,
			Arm arm) {
		if (armType != Type.SPINE)
			return null;
		return new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY(), 0);
	}

}
