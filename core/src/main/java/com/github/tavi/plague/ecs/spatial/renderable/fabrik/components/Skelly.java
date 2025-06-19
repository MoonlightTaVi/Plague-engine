package com.github.tavi.plague.ecs.spatial.renderable.fabrik.components;

import java.util.*;

public class Skelly {
	public Type type;
	public float time = 0f;
	
	private Map<ArmType, IdChain> arms = new HashMap<>();
	
	public Skelly(Type type) {
		this.type = type;
	}
	
	public IdChain growArm(ArmType type, int boneCount) {
		IdChain newArm = new IdChain(boneCount);
		arms.put(type, newArm);
		return newArm;
	}
	
	public ArmType[] arms() {
		return arms.keySet().stream().toArray(ArmType[]::new);
	}
	
	public IdChain get(ArmType type) {
		return arms.get(type);
	}
	
	
	public enum Type {
		HUMAN
	}
	
}
