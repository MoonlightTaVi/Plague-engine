package com.github.tavi.plague.ecs.spatial.renderable.ik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.renderable.ik.Arm.Type;

public class HumanMovementStrategy implements LimbMovementStrategy {
	private final Vector3 target = new Vector3();
	
	/**
	 * PLACEHOLDER 
	 * @return World mouse cursor position for spine, null otherwise
	 */
	@Override
	public Vector3 calculateTargetPosition(
			Vector3 baseOrigin,
			MovementState movementState,
			Type armType,
			Arm arm,
			float time
			) {
		switch (movementState) {
		case DANCING:
			
			switch (armType) {
			case SPINE:
				return danceSpine(baseOrigin, arm, time);
			default:
				break;
			}
			
			default:
				break;
		}
		return null;
	}
	
	private Vector3 danceSpine(
			Vector3 baseOrigin,
			Arm arm,
			float time
			) {
		float[][] frames = new float[][] {
			{ 0, 0, 1 },
			{ .2f, 0, .95f },
			{ .225f, 0, .9f },
			{ .2f, 0, .95f },
			{ 0, 0, 1 },
			{ -.2f, 0, .95f },
			{ -.225f, 0, .9f },
			{ -.2f, 0, .95f },
		};
		time = (time * 3f) % frames.length;
		int frame = (int) (time);
		target.set(frames[frame]);
		return target;
	}
	
	private Vector3 followMouse(Vector3 baseOrigin) {
		return new Vector3(
				Gdx.input.getX(),
				0,
				Gdx.graphics.getHeight() - 1 - Gdx.input.getY() // Find cursor Y position, then subtract "false" 50px of Y
				)
				.sub(baseOrigin).nor();
	}
}
