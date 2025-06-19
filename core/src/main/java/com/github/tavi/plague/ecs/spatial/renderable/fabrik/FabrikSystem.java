package com.github.tavi.plague.ecs.spatial.renderable.fabrik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.*;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.strategies.LimbMovementStrategy;
import com.github.tavi.plague.ecs.ECSystem;

public class FabrikSystem implements ECSystem {
	private Components components = Components.get();
	private FABRIK ik = new FABRIK(0.1f);
	private final FabrikData ikData = new FabrikData(components);
	
	private Skelly skelly = null;
	private MovementState state = null;
	private Transform transform = null;
	
	@Override
	public void process(float delta) {
		skelly.time += delta;
		LimbMovementStrategy strategy = LimbMovementStrategy.get(skelly.type);
		
		for (ArmType armType : skelly.arms()) {
			int[] armIds = skelly.get(armType).IDs;
			ikData.fromIds(armIds);
			
			Transform armTransform = components.get(armIds[0], Transform.class);
			if (armTransform == null) {
				ikData.withOrigin(transform.worldPosition);
			} else {
				ikData.withOrigin(armTransform.worldPosition);
			}
			ikData.build();
			
			Vector3 target = strategy.calculateTargetPosition(state, armType, skelly.time);
			if (target != null) {
				target.scl(ikData.chainLength).add(ikData.baseOrigin);
				
				ik.forPositions(ikData.positions)
				.ofLengths(ikData.lengths, ikData.chainLength)
				.reach(target);
			}
			ikData.updateTextures(armIds);
		}
	}
	
	@Override
	public boolean validate(int entityId) {
		skelly = components.get(entityId, Skelly.class);
		transform = components.get(entityId, Transform.class);
		state = components.get(entityId, MovementState.class);
		return ECSystem.super.areNotNull(skelly, transform, state);
	}

}
