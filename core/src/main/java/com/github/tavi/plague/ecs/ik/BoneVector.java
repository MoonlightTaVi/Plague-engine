package com.github.tavi.plague.ecs.ik;

import com.badlogic.gdx.math.Vector3;

/**
 * A Vector of a single Bone in the Arm.
 * Used in Inverse Kinematics. <br> <br>
 * Rotation of the BoneVector may be calculated by comparing
 * {@code Vector3 original} (must be static, i.e. never change)
 * and {@code Vector3 current}.
 */
public record BoneVector(Vector3 original, Vector3 current) {
	/**
	 * Constructs a BoneVector, taking its initial Vector3 value.
	 * @param original Initial Vector of the bone.
	 */
	public BoneVector(Vector3 original) {
		this(original, new Vector3(original));
	}

	/**
	 * A short-cut for the primary constructor.
	 * @param x X-coordinate of the {@code Vector3 original}.
	 * @param y Y-coordinate of the {@code Vector3 original}.
	 * @param z Z-coordinate of the {@code Vector3 original}.
	 * @see #BoneVector(Vector3)
	 */
	public BoneVector(float origX, float origY, float origZ) {
		this(new Vector3(origX, origY, origZ));
	}
	
}
