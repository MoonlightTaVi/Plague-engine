package com.github.tavi.plague.ecs.ik;

import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.util.IdChain;

/**
 * A data object, that serves as a mediator between
 * Inverse Kinematics algorithms and actual Entities inside the skeleton Arm.
 */
public class IkArm extends IdChain {
	public final ArmType armType;
	
	/**
	 * @param length Length of the Arm (number of bones).
	 * @see com.github.tavi.plague.ecs.util.IdChain#IdChain(int)
	 */
	public IkArm(int length, ArmType armType) {
		super(length);
		this.armType = armType;
	}
	
	/**
	 * Returns the Transform component of the first bone
	 * (which is the origin of the Arm).
	 * @return Transform component of the first bone inside the Arm.
	 */
	public Transform getOrigin() {
		return super.getEntity(0).get(Transform.class);
	}

}
