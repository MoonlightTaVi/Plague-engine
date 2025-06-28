package com.github.tavi.plague.ecs.fabrik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.states.MovementState;
import com.github.tavi.plague.ecs.ik.*;
import com.github.tavi.plague.ecs.ik.strategies.IkMovementStrategy;

/**
 * Used for calculations of Skeleton's limbs's target Vectors
 * (where they are needed to move to, accordingly to
 * the current Skeleton animation). <br>
 * Entity's Transform (for each bone) is then used to update its
 * Texture position (for rendering).
 * @see com.github.tavi.plague.ecs.ik.Skeleton
 * @see com.github.tavi.plague.ecs.states.MovementState
 * @see com.github.tavi.plague.ecs.spatial.Transform
 */
public class FabrikSystem implements ECSystem {
	private Components components = Components.get();
	private FABRIK ik = new FABRIK(0.1f);
	private final FabrikData ikData = new FabrikData();
	
	private Skeleton skelly = null;
	private MovementState state = null;
	private Transform transform = null;
	
	@Override
	public boolean validate(int entityId) {
		skelly = components.get(entityId, Skeleton.class);
		transform = components.get(entityId, Transform.class);
		state = components.get(entityId, MovementState.class);
		return ECSystem.super.areNotNull(skelly, transform, state);
	}
	
	@Override
	public void process(float delta) {
		// For animation frames
		skelly.time += delta;
		// Different animations for different Skeletons
		IkMovementStrategy strategy = IkMovementStrategy.get(skelly.type);
		
		for (IkArm arm : skelly.arms()) {
			ikData.fromIkArm(arm);
			
			Transform armTransform = arm.getOrigin();
			if (armTransform == null) {
				// Arm grows from Skeleton's Transform position
				ikData.withOrigin(transform.worldPosition);
			} else {
				// Arm grows from another Arm
				ikData.withOrigin(armTransform.worldPosition);
			}
			ikData.buildChain();
			
			Vector3 target = strategy.calculateTargetPosition(
					state,
					arm.armType,
					skelly.time
					);
			if (target != null) {
				/*
				 *  Target is relative to the baseOrigin and normal
				 *  (has the length of 1f) by default.
				 */
				target.scl(ikData.chainLength).add(ikData.baseOrigin);
				
				/*
				 *  Note: BoneVector is used in computations
				 *  of Textures rotation (for rendering),
				 *  so it does not matter if Transform or BoneVector
				 *  components are used; anyway it is needed to
				 *  chain / unchain Vectors, all at a time or one by one.
				 */
				ik.forPositions(ikData.positions)
				.ofLengths(ikData.lengths, ikData.chainLength)
				.reach(target);
			}
			ikData.updateTransforms(arm);
		}
	}

}
