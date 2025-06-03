package com.github.tavi.plague.ecs.spatial.renderable.ik;

import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.renderable.TextureMeta;
import com.github.tavi.plague.ecs.spatial.Transform;

/**
 * The simplest (and the less efficient) Inverse Kinematics algorithm. <br>
 * Brute-forces the solution after a couple of hundreds of iterations.
 */
public class FABRIK {
	private Components components = Components.get();
	/** The accuracy of the algorithm: the largest acceptable distance to the target. */
	private final float marginOfError;
	
	/**
	 * Constructs the IK solution with a given {@code marginOfError}
	 * @param marginOfError The accuracy of the algorithm: the largest acceptable distance to the target.
	 */
	public FABRIK(float marginOfError) {
		this.marginOfError = marginOfError;
	}
	
	/** IDs of Entities, representing Arms. */
	private int[] armIds = null;
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
	
	
	/**
	 * A simple all-in-one method for FABRIK calculation.
	 * @param armIds IDs of the Entities, which represent bones 
	 * (must have a {@link TextureMeta} Component).
	 * @param baseOrigin From where the Arm begins (its rotation pivot).
	 * @param target The position the Arm is trying to reach.
	 */
	public void process(int[] armIds, Vector3 baseOrigin, Vector3 target) {
		setup(armIds);
		prepareData(baseOrigin);
		reach(target);
		updateTexturePositions();
		resetData();
	}
	
	
	/**
	 * Retrieves the Vectors of the bones by the IDs of the corresponding Entities
	 * and caches them.
	 * @param armIds IDs of the Entities, which represent bones .
	 */
	public void setup(int[] armIds) {
		this.armIds = armIds;
		int chainSize = armIds.length + 1;
		positions = new Vector3[chainSize];
		lengths = new float[chainSize];
		lengths[0] = 0f;
		armLength = 0f;
		for (int i = 1; i < chainSize; i++) {
			BoneVector vector = null;
			if ((vector = components.get(armIds[i - 1], BoneVector.class)) == null) {
				throw new IllegalStateException(
						String.format(
								"Entity with ID %s does not have a %s property, but is forced into processing by IK",
								BoneVector.class.getName(),
								armIds[i]
										)
						);
			}
			positions[i] = vector.current();
			lengths[i] = vector.original().len();
			armLength += lengths[i];
		}
	}
	
	/**
	 * Sums each bone Vector from {@code i = 1} with its parent (previous bone Vector).
	 * <br>
	 * The {@link FABRIK} works with World Space coordinates;
	 * so, in case we have our bone Vectors taking origin at {@code (0;0;0)} (by default),
	 * it is needed to make a chain of bones to form the Arm.
	 * @param baseOrigin The beginning of the Arm ( {@link #positions}{@code [0] = baseOrigin} ).
	 */
	public void prepareData(Vector3 baseOrigin) {
		int chainSize = positions.length;
		positions[0] = baseOrigin;
		initialPosition.set(positions[0]);
		for (int i = 1; i < chainSize; i++) {
			positions[i].add(positions[i - 1]);
		}
	}
	
	/**
	 * Updates the {@link Transform} Components of the Entities in the Arm.
	 * <br>
	 * Transform is then used for rendering.
	 */
	public void updateTexturePositions() {
		for (int i = 0; i < armIds.length; i++) {
			components.getOr(armIds[i], Transform.class, () -> new Transform()).set(positions[i]);
		}
	}
	
	/**
	 * {@code (0;0;0)} centered Vectors are used to calculate the rotation of an Entity,
	 * thus they need to be reset to defaults.
	 * @see #prepareData(Vector3)
	 */
	public void resetData() {
		int chainSize = positions.length;
		for (int i = chainSize - 1; i > 0; i--) {
			positions[i].sub(positions[i - 1]);
		}
	}
	
	
	/**
	 * Returns true if the FABRIK has made enough iterations and need to stop.
	 * @param target The position the Arm is trying to reach.
	 * @return {@code true} if the distance from the Arm tip to the {@code target}
	 * is less than {@link #marginOfError}.
	 */
	private boolean inMarginOfError(Vector3 target) {
		return positions[positions.length - 1].dst(target) < marginOfError;
	}
	
	/**
	 * Starter method. <br>
	 * Starts the FABRIK calculation.
	 * @param target The position the Arm is trying to reach.
	 */
	private void reach(Vector3 target) {
		if (positions[0].dst(target) > armLength) {
			return;
		}
		
		for (int i = 0; i < 1000 && !inMarginOfError(target); i++) {
			iterate(target);
		}
	}
	
	/**
	 * The main FABRIK method. <br>
	 * Tries to move the bones towards the target in a natural way
	 * (need a couple of hundreds of calls to achieve the desired result).
	 * @param target The position the Arm is trying to reach.
	 */
	private void iterate(Vector3 target) {
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
