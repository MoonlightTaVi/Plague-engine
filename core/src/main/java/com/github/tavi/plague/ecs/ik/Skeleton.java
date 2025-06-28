package com.github.tavi.plague.ecs.ik;

import java.util.*;

public class Skeleton {
	public SkeletonType type;
	public float time = 0f;
	
	private Map<ArmType, IkArm> arms = new HashMap<>();
	
	public Skeleton(SkeletonType type) {
		this.type = type;
	}
	
	/**
	 * Adds a new empty Arm to the Skeleton, given the number of bones.
	 * @param type Type of the Arm.
	 * @param boneCount Number of bones in the Arm.
	 * @return This new, empty Arm for later use (e.g. to fill it with bones).
	 */
	public IkArm growArm(ArmType type, int boneCount) {
		IkArm newArm = new IkArm(boneCount, type);
		arms.put(type, newArm);
		return newArm;
	}
	
	/**
	 * Returns all the Arms of this Skeleton.
	 * @return values() Collection of the HashMap, containing the Arms
	 * of this Skeleton.
	 */
	public Collection<IkArm> arms() {
		return arms.values();
	}
	
	/**
	 * Get an IkArm of this skeleton by its type.
	 * @param type Type of the Arm
	 * @return IkArm with the corresponding type if present,
	 * null otherwise.
	 */
	public IkArm get(ArmType type) {
		return arms.get(type);
	}
	
}
