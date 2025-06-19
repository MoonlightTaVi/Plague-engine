package com.github.tavi.plague.ecs.spatial.renderable.fabrik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.DirectionalVector;
import com.github.tavi.plague.util.vectors.VectorChain;

public class FabrikData implements VectorChain {
	private final Components components;
	
	public Vector3 baseOrigin = new Vector3();
	public Vector3[] positions = null;
	public float[] lengths;
	public float chainLength = 0f;
	
	public FabrikData(Components components) {
		this.components = components;
	}

	public FabrikData fromIds(int[] idChain) {
		positions = idsToVectors(idChain);
		return this;
	}
	public FabrikData withOrigin(Vector3 baseOrigin) {
		this.baseOrigin.set(baseOrigin);
		return this;
	}
	
	@Override
	public void build() {
		VectorChain.super.chainVectors(positions);
	}
	
	public void updateTextures(int[] armIds) {
		for (int i = 0; i < armIds.length; i++) {
			components.getOr(armIds[i], Transform.class, () -> new Transform()).setWorldPosition(positions[i]);
		}
		
		VectorChain.super.unchainVectors(positions);
		positions = null;
		lengths = null;
		chainLength = 0f;
	}
	
	private Vector3[] idsToVectors(int[] idsArray) {
		int chainSize = idsArray.length + 1;
		Vector3[] positions = new Vector3[chainSize];
		lengths = new float[chainSize];
		lengths[0] = 0f;
		chainLength = 0f;
		
		positions[0] = baseOrigin;
		for (int i = 1; i < chainSize; i++) {
			DirectionalVector vector = null;
			if ((vector = components.get(idsArray[i - 1], DirectionalVector.class)) == null) {
				throw new IllegalStateException(
						String.format(
								"Entity with ID %d does not have a %s property, but is forced into processing by IK",
								idsArray[i - 1],
								DirectionalVector.class.getName()
										)
						);
			}
			positions[i] = vector.current();
			lengths[i] = vector.original().len();
			chainLength += lengths[i];
		}
		return positions;
	}

}
