package com.github.tavi.plague.sciacallo;

import java.util.HashMap;
import java.util.Map;

import com.github.tavi.plague.shared.Components;

public class Membra implements Components<Integer> {
	
	private static volatile Membra INSTANCE = null;
	
	/**
	 * Get a singleton instance of the {@code Membra} class
	 * @return Membra singleton
	 * @see Components
	 */
	public static Membra get() {
		if (INSTANCE == null) {
			synchronized (Membra.class) {
				INSTANCE = new Membra();
			}
		}
		return INSTANCE;
	}
	
	/**
	 * This HashMap contains all the components of all the entities. <br>
	 * As a key, a generated hash code of {@code <entityId + componentType>} is used
	 */
	private Map<Integer, Object> components = new HashMap<>();

	// Registering 100_000 components at once takes ~28ms (a transient drop to 35FPS; it is a satisfactory result)
	@Override
	public <COMPONENT> COMPONENT register(Integer entity, Class<COMPONENT> componentType, COMPONENT component) throws IllegalArgumentException {
		int hash = getHash(entity, componentType);
		if (components.containsKey(hash)) {
			Class<?> existentObject = components.get(hash).getClass();
			String existent = String.format("entityId:%d, componentType:%s", getIdFromHash(hash, existentObject), existentObject.getName());
			String problematic = String.format("entityId:%d, componentType:%s", entity, componentType.getName());
			throw new IllegalArgumentException(String.format("Component already registered: %d\n\tExistent: %s\n\tTrying to add: %s", hash, existent, problematic));
		}
		components.put(hash, component);
		return null;
	}

	// Tests show ~11ms on retrieving 100_000 components in a "while" loop, which is more than 76FPS (a good result)
	// Keep in mind, that the test took place on a budget lap-top, with few-to-none background processes
	// (when doing the same with a browser (playing a video) at the same time, the latency is somewhat ~19ms)
	@Override
	public <COMPONENT> COMPONENT get(Integer entity, Class<COMPONENT> componentType) {
		int hash = getHash(entity, componentType);
		if (components.containsKey(hash)) {
			return componentType.cast(components.get(hash));
		}
		return null;
	}
	
	/**
	 * Generates a hash code, corresponding to the key inside {@link #components} HashMap
	 * @param <COMPONENT> Type of a component
	 * @param id Identifier of an entity
	 * @param componentType Class of a component
	 * @return Generated hash code of the component of the entity
	 */
	private <COMPONENT> int getHash(int id, Class<COMPONENT> componentType) {
		int result = 1;
		
		result = ((result << 5) - result) + id;
		result = ((result << 5) - result) + componentType.hashCode();
		
		return result;
	}
	
	private <COMPONENT> int getIdFromHash(int hash, Class<COMPONENT> componentType) {
		int result = hash - componentType.hashCode();
		result /= 31;
		result -= 31;
		return result;
	}

}
