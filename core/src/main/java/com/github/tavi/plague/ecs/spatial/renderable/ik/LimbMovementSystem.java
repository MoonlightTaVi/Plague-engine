package com.github.tavi.plague.ecs.spatial.renderable.ik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.VisibleSystem;

public class LimbMovementSystem implements VisibleSystem {
	private Components components = Components.get();
	private FABRIK ik = new FABRIK(0.1f);
	
	@Override
	public void process(int entityId, float delta) {
		Skelly skelly;
		MovementState state;
		if (
				(skelly = components.get(entityId, Skelly.class)) == null ||
				(state = components.get(entityId, MovementState.class)) == null
				) {
			return;
		}
		LimbMovementStrategy strategy = LimbMovementStrategy.get(skelly.type);
		
		Transform t = components.get(entityId, Transform.class);
		Vector3 baseOrigin = new Vector3();
		if (t != null) {
			baseOrigin.set(t.x, t.y, t.z);
		}
		
		for (Arm.Type armType : skelly.arms()) {
			Arm arm = skelly.get(armType);
			Vector3 target = strategy.calculateTargetPosition(baseOrigin, state, armType, arm);
			ik.process(arm.IDs, baseOrigin, target);
		}
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
