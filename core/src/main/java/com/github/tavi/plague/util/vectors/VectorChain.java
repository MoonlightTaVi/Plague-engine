package com.github.tavi.plague.util.vectors;

import com.badlogic.gdx.math.Vector3;

public interface VectorChain {
	public void buildChain();
	
	default void chainVectors(Vector3[] positions) {
		for (int i = 1; i < positions.length; i++) {
			positions[i].add(positions[i - 1]);
		}
	}
	default void unchainVectors(Vector3[] positions) {
		for (int i = positions.length - 1; i > 0; i--) {
			positions[i].sub(positions[i - 1]);
		}
	}
	
}
