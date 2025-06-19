package com.github.tavi.plague.ecs.spatial.renderable.fabrik.strategies;

import java.util.*;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.ArmType;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.Skelly;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.Skelly.Type;

public interface LimbMovementStrategy {
	
	public static Map<Skelly.Type, LimbMovementStrategy> strategies = new HashMap<>();
	public static void setupStrategies() {
		strategies.put(Skelly.Type.HUMAN, new HumanMovementStrategy());
	}
	public static LimbMovementStrategy get(Skelly.Type skellyType) {
		return strategies.get(skellyType);
	}

	public Vector3 calculateTargetPosition(
			MovementState movementState, 
			ArmType armType, 
			float time
			);
	
}
