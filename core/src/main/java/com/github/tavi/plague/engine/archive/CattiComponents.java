package com.github.tavi.plague.engine.archive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The {@code Sciacallo} version of the Components. <br>
 * The name of the class (Latin "Cats") is a reference to Schrödinger's cat:
 * just like with the said paradox, you cannot be sure, whether an entity
 * has some component or it hasn't; not earlier than you try to request it. <br>
 * This also means, one cannot iterate through all the components of an entity;
 * it is possible to iterate through all the components of all entities, though
 * (which may hurt performance very much).
 */
public class CattiComponents implements Components {
	
	private static volatile CattiComponents INSTANCE = null;
	
	/**
	 * Get a singleton instance of the {@code CattiComponents} class
	 * @return CattiComponents singleton
	 * @see Components
	 */
	public static CattiComponents get() {
		if (INSTANCE == null) {
			synchronized (CattiComponents.class) {
				if (INSTANCE == null) {
					INSTANCE = new CattiComponents();
				}
			}
		}
		return INSTANCE;
	}
	
	/**
	 * This HashMap contains all the components of all the entities. <br>
	 * As a key, a generated hash code of {@code <entityId + componentClass>} is used
	 */
	private Map<Integer, Object> components = new HashMap<>();

	// Registering 100_000 components at once takes ~28ms (a transient drop to 35FPS; it is a satisfactory result)
	@Override
	public <COMPONENT> COMPONENT register(int entity, Class<COMPONENT> componentClass, COMPONENT component) throws IllegalArgumentException {
		int hash = getHash(entity, componentClass);
		if (components.containsKey(hash)) {
			Class<?> existentObject = components.get(hash).getClass();
			String existent = String.format("entityId:%d, componentType:%s", getIdFromHash(hash, existentObject), existentObject.getName());
			String problematic = String.format("entityId:%d, componentType:%s", entity, componentClass.getName());
			throw new IllegalArgumentException(String.format("Component already registered: %d\n\tExistent: %s\n\tTrying to add: %s", hash, existent, problematic));
		}
		components.put(hash, component);
		return component;
	}

	@Override
	public <COMPONENT> COMPONENT register(int entity, COMPONENT component) throws IllegalArgumentException {
		int hash = getHash(entity, component.getClass());
		if (components.containsKey(hash)) {
			Class<?> existentObject = components.get(hash).getClass();
			String existent = String.format("entityId:%d, componentType:%s", getIdFromHash(hash, existentObject), existentObject.getName());
			String problematic = String.format("entityId:%d, componentType:%s", entity, component.getClass().getName());
			throw new IllegalArgumentException(String.format("Component already registered: %d\n\tExistent: %s\n\tTrying to add: %s", hash, existent, problematic));
		}
		components.put(hash, component);
		return component;
	}

	// Tests show ~11ms on retrieving 100_000 components in a "while" loop, which is more than 76FPS (a good result)
	// Keep in mind, that the test took place on a budget lap-top, with few-to-none background processes
	// (when doing the same with a browser (playing a video) at the same time, the latency is somewhat ~19ms)
	@Override
	public <COMPONENT> COMPONENT get(int entity, Class<COMPONENT> componentClass) {
		int hash = getHash(entity, componentClass);
		if (components.containsKey(hash)) {
			return componentClass.cast(components.get(hash));
		}
		return null;
	}

	@Override
	public <COMPONENT> COMPONENT getOr(int entity, Class<COMPONENT> componentClass, Supplier<COMPONENT> defaultValue) {
		COMPONENT result = get(entity, componentClass);
		
		if (result == null) {
			result = register(entity, componentClass, defaultValue.get());
		}
		
		return result;
	}
	
	/**
	 * Generates a hash code, corresponding to the key inside {@link #components} HashMap
	 * @param <COMPONENT> ArmType of the component
	 * @param id Identifier of an entity
	 * @param componentClass Class of the component
	 * @return Generated hash code of the component of the entity
	 */
	private <COMPONENT> int getHash(int id, Class<COMPONENT> componentClass) {
		int result = 1;
		
		result = ((result << 5) - result) + id;
		result = ((result << 5) - result) + componentClass.hashCode();
		
		return result;
	}
	
	/**
	 * 
	 * @param <COMPONENT> - ArmType of the component inside the entry
	 * @param hash - Key of the components inside the {@link #components} HashMap
	 * @param componentClass - Class of the component
	 * @return Id of the entity this component belongs to
	 */
	private <COMPONENT> int getIdFromHash(int hash, Class<COMPONENT> componentClass) {
		int result = hash - componentClass.hashCode();
		result /= 31;
		result -= 31;
		return result;
	}

	@Override
	public <COMPONENT> boolean unregister(int entity, Class<COMPONENT> componentClass) {
		int hash = getHash(entity, componentClass);
		return components.remove(hash) != null;
	}

}
