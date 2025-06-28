package com.github.tavi.plague.ecs.ik.strategies;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.ik.ArmType;
import com.github.tavi.plague.ecs.states.MovementState;

public class HumanMovementStrategy implements IkMovementStrategy {
	private final Vector3 target = new Vector3();
	
	/**
	 * PLACEHOLDER 
	 * @return World mouse cursor position for spine, null otherwise
	 */
	@Override
	public Vector3 calculateTargetPosition(
			MovementState movementState,
			ArmType armType,
			float time
			) {
		switch (movementState) {
		case DANCING:
			
			switch (armType) {
			case SPINE:
				return danceSpine(time);
			default:
				break;
			}
			
			default:
				break;
		}
		return null;
	}
	
	private Vector3 danceSpine(
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
}
