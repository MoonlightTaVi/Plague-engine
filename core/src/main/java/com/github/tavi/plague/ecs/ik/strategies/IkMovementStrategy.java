package com.github.tavi.plague.ecs.ik.strategies;

import java.util.*;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.ik.ArmType;
import com.github.tavi.plague.ecs.ik.SkeletonType;
import com.github.tavi.plague.ecs.states.MovementState;

/**
 * Used in Inverse Kinematics find-target-of-movement calculations for Arms.
 */
public interface IkMovementStrategy {
	/** Contains all strategies used by various types of Skeletons. */
	public static Map<SkeletonType, IkMovementStrategy> strategies = new HashMap<>();
	/** Sets up configuration for IkMovementStrategy implementations. */
	public static void setupStrategies() {
		strategies.put(SkeletonType.HUMAN, new HumanMovementStrategy());
	}
	/**
	 * Returns the implementation of IkMovementStrategy for the
	 * given SkeletonType.
	 * @param skellyType Type of the skeleton to animate.
	 * @return Movement Strategy for this type of skeleton.
	 */
	public static IkMovementStrategy get(SkeletonType skellyType) {
		return strategies.get(skellyType);
	}

	/**
	 * Returns the target Vector for the specified Arm and Arm's current animation. <br>
	 * The resulting Vector is restricted to a square with the sides of 1f and
	 * is centered at (0; 0), so it must be adjusted (accordingly to the actual
	 * base origin of the Arm and its length in pixels).
	 * @param movementState Animation state of the Arm.
	 * @param armType Type of the Arm.
	 * @param time Time passed since the animation has started (for frames).
	 * @return IK target Vector for the Arm.
	 */
	public Vector3 calculateTargetPosition(
			MovementState movementState, 
			ArmType armType, 
			float time
			);
	
}
