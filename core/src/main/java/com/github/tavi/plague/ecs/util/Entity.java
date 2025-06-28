package com.github.tavi.plague.ecs.util;

import java.util.function.Supplier;

import com.github.tavi.plague.ecs.Components;

/**
 * A mediator class to make cooperation between EC Systems
 * and Components easier in ECS engine.
 * @see com.github.tavi.plague.ecs.ECSystem
 * @see com.github.tavi.plague.ecs.Components
 */
public class Entity {
	/** A storage of components. */
	private final Components components = Components.get();
	/** This Entity's ID number. */
	public final int id;
	/**
	 * Construct the mediator for the Entity with the given ID number.
	 * @param entityId
	 */
	public Entity(int entityId) {
		id = entityId;
	}
	
	/**
	 * Returns a component of this Entity, given the class of the component.
	 * @param <T> Type of the component.
	 * @param componentClass Class of the component.
	 * @return This component if its present, {@code null} otherwise.
	 */
	public <T> T get(Class<T> componentClass) {
		return components.get(id, componentClass);
	}
	
	/**
	 * Returns a component of this Entity, given the class of the component.
	 * @param <T> Type of the component.
	 * @param componentClass Class of the component.
	 * @param defaultValue Supplier that returns the default value for the component.
	 * @return This component if its present, {@code defaultValue.get()} otherwise.
	 */
	public <T> T getOr(Class<T> componentClass, Supplier<T> defaultValue) {
		T component = components.getOr(id, componentClass, defaultValue);
		return component;
	}
	
	/**
	 * Returns a required component of this Entity, given the class of the component.
	 * @param <T> Type of the component.
	 * @param componentClass Class of the component.
	 * @return This component if its present.
	 * @throws IllegalStateException if the Entity does not have such a component.
	 */
	public <T> T getRequired(Class<T> componentClass) throws IllegalStateException {
		T component = components.get(id, componentClass);
		if (component == null) {
			throw new IllegalStateException(
					String.format(
							"Entity with ID %d does not have a required %s property.",
							id,
							componentClass.getName()
									)
					);
		}
		return component;
	}
	

}
