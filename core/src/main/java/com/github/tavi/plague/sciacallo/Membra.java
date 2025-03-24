package com.github.tavi.plague.sciacallo;

import java.util.HashMap;
import java.util.Map;

import com.github.tavi.plague.shared.Components;

public class Membra implements Components<Integer> {
	
	private static volatile Membra INSTANCE = null;
	
	public static Membra get() {
		if (INSTANCE == null) {
			synchronized (Membra.class) {
				INSTANCE = new Membra();
			}
		}
		return INSTANCE;
	}
	
	private Map<MembrumEntry<?>, Object> components = new HashMap<>();

	@Override
	public <COMPONENT> COMPONENT register(Integer entity, Class<COMPONENT> componentType, COMPONENT component) {
		MembrumEntry<COMPONENT> newEntry = new MembrumEntry<>(entity, componentType);
		components.put(newEntry, component);
		return null;
	}

	// Tests show ~34ms on retrieving 100_000 components in a "while" loop, which is less than 60FPS
	// First time calling a method takes ~13ms though (because not cached)
	@Override
	public <COMPONENT> COMPONENT get(Integer entity, Class<COMPONENT> componentType) {
		MembrumEntry<COMPONENT> entry = new MembrumEntry<>(entity, componentType);
		if (components.containsKey(entry)) {
			return componentType.cast(components.get(entry));
		}
		return null;
	}
	
	
	record MembrumEntry<COMPONENT>(Integer entity, Class<COMPONENT> componentType) { }

}
