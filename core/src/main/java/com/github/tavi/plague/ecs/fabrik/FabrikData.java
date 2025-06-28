package com.github.tavi.plague.ecs.fabrik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.ik.BoneVector;
import com.github.tavi.plague.ecs.ik.IkArm;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.util.Entity;
import com.github.tavi.plague.util.vectors.VectorChain;

/**
 * Data object, used to make a chain of Vectors
 * for the FABRIK algorithm, then update bone Transforms
 * for rendering and reset Vectors back again.
 */
public class FabrikData implements VectorChain {
	public Vector3 baseOrigin = new Vector3();
	public Vector3[] positions = null;
	public float[] lengths;
	public float chainLength = 0f;

	public FabrikData fromIkArm(IkArm ikArm) {
		positions = setupVectors(ikArm);
		return this;
	}
	public FabrikData withOrigin(Vector3 baseOrigin) {
		this.baseOrigin.set(baseOrigin);
		return this;
	}
	
	/**
	 * Chains the Vectors in the Arm.
	 */
	@Override
	public void buildChain() {
		VectorChain.super.chainVectors(positions);
	}
	
	/**
	 * Updates {@code Transform} components of the bone Entities inside IkArm
	 * after FABRIK was solved (for rendering); then resets bones' Vectors to their
	 * initial state (for rotation).
	 */
	public void updateTransforms(IkArm ikArm) {
		for (int i = 0; i < positions.length - 1; i++) {
			ikArm.getEntity(i )
			.getOr(Transform.class, () -> new Transform())
			.worldPosition.set(positions[i]);
		}
		
		VectorChain.super.unchainVectors(positions);
		positions = null;
		lengths = null;
		chainLength = 0f;
	}
	
	private Vector3[] setupVectors(IkArm ikArm) {
		int chainSize = ikArm.size + 1;
		Vector3[] positions = new Vector3[chainSize];
		lengths = new float[chainSize];
		lengths[0] = 0f;
		chainLength = 0f;
		
		positions[0] = baseOrigin;
		int i = 1;
		for (Entity entity : ikArm.entities()) {
			BoneVector vector = entity.getRequired(BoneVector.class);
			positions[i] = vector.current();
			lengths[i] = vector.original().len();
			chainLength += lengths[i];
			i++;
		}
		return positions;
	}

}
