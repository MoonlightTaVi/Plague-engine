package com.github.tavi.plague.shared.systems;

/**
 * Generic Entity-Component system, applicable to the in-game Entities
 * @param <ID> A value used to identify Entities; an identifier of Entities
 */
public interface EntityProcessor<ID> {

	/**
	 * The method contains all the logic, applied to an Entity
	 * during the execution of this system
	 * @param entityId Identifier of the Entity being processed
	 */
	public void process(ID entityId);
	
}
