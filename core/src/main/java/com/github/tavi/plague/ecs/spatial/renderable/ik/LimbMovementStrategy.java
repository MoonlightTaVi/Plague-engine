package com.github.tavi.plague.ecs.spatial.renderable.ik;

import java.util.*;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.behaviour.MovementState;

public interface LimbMovementStrategy {
	
	public static Map<Skelly.Type, LimbMovementStrategy> strategies = new HashMap<>();
	public static void setupStrategies() {
		strategies.put(Skelly.Type.HUMAN, new HumanMovementStrategy());
	}
	public static LimbMovementStrategy get(Skelly.Type skellyType) {
		return strategies.get(skellyType);
	}

	public Vector3 calculateTargetPosition(Vector3 baseOrigin, MovementState movementState, Arm.Type armType, Arm arm);
	
}
