package com.github.tavi.plague.ecs;

/**
 * Entity-Component system, applicable to the in-game Entities. <br>
 * An {@code int} value is used as a standard for an entity's id, but
 * {@code Entity Manager} s also provide methods for tagging objects with
 * different types.
 */
public interface System {

	/**
	 * The method contains all the logic, applied to an Entity
	 * during the execution of this system
	 * @param entityId Identifier of the Entity being processed
	 */
	public void process(int entityId);
	
}
