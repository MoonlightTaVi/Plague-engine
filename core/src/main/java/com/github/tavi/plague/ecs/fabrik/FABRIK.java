package com.github.tavi.plague.ecs.fabrik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.renderable.TextureMeta;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.engine.archive.Components;

/**
 * The simplest (and the less efficient) Inverse Kinematics algorithm. <br>
 * Brute-forces the solution after a couple of hundreds of iterations.
 */
public class FABRIK {
	/** The accuracy of the algorithm: the largest acceptable distance to the target. */
	private final float marginOfError;
	
	/**
	 * Constructs the IK solution with a given {@code marginOfError}
	 * @param marginOfError The accuracy of the algorithm: the largest acceptable distance to the target.
	 */
	public FABRIK(float marginOfError) {
		this.marginOfError = marginOfError;
	}
	
	/** Initial beginning of the Arm. */
	private Vector3 initialPosition = new Vector3();
	/**
	 * Vectors of the bone joints.
	 * The first Vector here is the origin of the Arm;
	 * then go the bones' end points.
	 */
	private Vector3[] positions = null;
	/**
	 * Lengths of each bone segment.
	 * Since the first bone Vector is the origin of the Arm,
	 * {@code lengths[0]} equals {@code 0f}.
	 */
	private float[] lengths = null;
	/** The overall length of the Arm (how far it can reach). */
	private float armLength = 0f;
	

	public FABRIK forPositions(Vector3[] positions) {
		this.positions = positions;
		return this;
	}
	public FABRIK ofLengths(float[] lengths, float lengthsSum) {
		this.lengths = lengths;
		armLength = lengthsSum;
		return this;
	}
	
	/**
	 * Starter method. <br>
	 * Starts the FABRIK calculation.
	 * @param target The position the Arm is trying to reach.
	 */
	public void reach(Vector3 target) {
		if (positions[0].dst(target) > armLength) {
			target.set(new Vector3(target)
					.sub(positions[0])
					.scl(armLength)
					.add(positions[0])
					);
		}
		
		for (int i = 0; i < 1000 && !inMarginOfError(positions[positions.length - 1], target); i++) {
			iterate(target);
		}
		positions = null;
		lengths = null;
		armLength = 0f;
	}
	
	/**
	 * Returns true if the FABRIK has made enough iterations and need to stop.
	 * @param target The position the Arm is trying to reach.
	 * @return {@code true} if the distance from the Arm tip to the {@code target}
	 * is less than {@link #marginOfError}.
	 */
	private boolean inMarginOfError(Vector3 armTip, Vector3 target) {
		return armTip.dst(target) < marginOfError;
	}
	
	/**
	 * The main FABRIK method. <br>
	 * Tries to move the bones towards the target in a natural way
	 * (need a couple of hundreds of calls to achieve the desired result).
	 * @param target The position the Arm is trying to reach.
	 */
	private void iterate(Vector3 target) {
		initialPosition.set(positions[0]);
		int chainSize = positions.length;
		// Backward
		positions[chainSize - 1].set(target);
		for (int i = chainSize - 2; i >= 0; i--) {
			float length0 = lengths[i + 1];
			float length1 = positions[i + 1].dst(positions[i]);
			float lambda = length0 / length1;
			positions[i]
				.set(new Vector3(positions[i + 1]).scl(1 - lambda)
						.add(new Vector3(positions[i]).scl(lambda)));
		}
		// Forward
		positions[0].set(initialPosition);
		for (int i = 0; i < chainSize - 2; i++) {
			float length0 = lengths[i + 1];
			float length1 = positions[i + 1].dst(positions[i]);
			float lambda = length0 / length1;
			positions[i + 1]
				.set(new Vector3(positions[i]).scl(1 - lambda)
						.add(new Vector3(positions[i + 1]).scl(lambda)));
		}
	}

}
