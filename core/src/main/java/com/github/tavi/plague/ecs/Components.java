package com.github.tavi.plague.ecs;

import java.util.function.Supplier;

/**
 * A storage of all the components, belonging to all the Entities.
 * @see com.github.tavi.plague.ecs.CattiComponents
 */
public interface Components {
	
	public static Components get() {
		return CattiComponents.get();
	}
	

	/**
	 * Adds the specified component to the specified entity <br>
	 * The difference from the {@link #register(Object, Object)} is that the component's
	 * interface / superclass can be used as the key of the component entry 
	 * (which allows {@code dependency inversion}, e.g. using different implementations of one component).
	 * @param <COMPONENT> Type of the component
	 * @param entity Identifier of the Entity
	 * @param componentClass Interface of the component
	 * @param component Actual value of the component
	 * @return The component passed as the last argument for later use
	 * @throws IllegalArgumentException If trying to register a component that potentially already exists
	 */
	public <COMPONENT> COMPONENT register(int entity, Class<COMPONENT> componentClass, COMPONENT component) throws IllegalArgumentException;

	/**
	 * Adds the specified component to the specified entity. <br>
	 * The difference from the {@link #register(Object, Class, Object)} is that the component's
	 * interface / superclass cannot be used as the key of the component entry 
	 * (instead, the component's class itself is directly used).
	 * @param <COMPONENT> Type of the component
	 * @param entity Identifier of the Entity
	 * @param componentType Interface of the component
	 * @param component Actual value of the component
	 * @return The component passed as the last argument for later use
	 * @throws IllegalArgumentException If trying to register a component that potentially already exists
	 */
	public <COMPONENT> COMPONENT register(int entity, COMPONENT component) throws IllegalArgumentException;
	
	/**
	 * Try to retrieve a component of the specified type, belonging to the entity
	 * @param <COMPONENT> Type of the component
	 * @param entity Identifier of the Entity
	 * @param componentClass Interface of the component
	 * @return The requested component if existent, {@code null} otherwise
	 */
	public <COMPONENT> COMPONENT get(int entity, Class<COMPONENT> componentClass);
	
	
	public <COMPONENT> COMPONENT getOr(int entity, Class<COMPONENT> componentClass, Supplier<COMPONENT> defaultValue);
	
}
