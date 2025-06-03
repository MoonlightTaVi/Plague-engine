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
		return new Vector3(
				Gdx.input.getX(),  // X is the same
				50, // 50px away from camera
				Gdx.graphics.getHeight() - 1 - Gdx.input.getY() - 50 // Find cursor Y position, then subtract "false" 50px of Y
				);
	}

}
