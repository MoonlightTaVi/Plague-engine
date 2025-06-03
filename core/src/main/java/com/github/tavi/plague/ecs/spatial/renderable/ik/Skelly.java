package com.github.tavi.plague.ecs.spatial.renderable.ik;

import java.util.*;

public class Skelly {
	public Type type;
	
	private Map<Arm.Type, Arm> arms = new HashMap<>();
	
	public Skelly(Type type) {
		this.type = type;
	}
	
	public Arm growArm(Arm.Type type, int boneCount) {
		Arm newArm = new Arm(boneCount);
		arms.put(type, newArm);
		return newArm;
	}
	
	public Arm.Type[] arms() {
		return arms.keySet().stream().toArray(Arm.Type[]::new);
	}
	
	public Arm get(Arm.Type type) {
		return arms.get(type);
	}
	
	
	public enum Type {
		HUMAN
	}
	
}
