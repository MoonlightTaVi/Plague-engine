package com.github.tavi.plague.shared;

/**
 * A storage of all the components, belonging to all the Entities
 * @param <ENTITY> A type used to identify an Entity
 */
public interface Components<ENTITY> {

	/**
	 * Adds the specified component to the specified entity
	 * @param <COMPONENT> Type of the component
	 * @param entity Identifier of the Entity
	 * @param componentType Interface of the component
	 * @param component Actual value of the component
	 * @return The component passed as the last argument for later use
	 * @throws IllegalArgumentException If trying to register a component that potentially already exists
	 */
	public <COMPONENT> COMPONENT register(ENTITY entity, Class<COMPONENT> componentType, COMPONENT component) throws IllegalArgumentException;
	
	/**
	 * Try to retrieve a component of the specified type, belonging to the entity
	 * @param <COMPONENT> Type of the component
	 * @param entity Identifier of the Entity
	 * @param componentType Interface of the component
	 * @return The requested component if existent, {@code null} otherwise
	 */
	public <COMPONENT> COMPONENT get(ENTITY entity, Class<COMPONENT> componentType);
	
}
