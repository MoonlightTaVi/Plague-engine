package com.github.tavi.plague.engine.archive;

import com.github.tavi.plague.util.NotNullValidator;

/**
 * Entity-Component system, applicable to the in-game Entities. <br>
 * An {@code int} value is used as a standard for an entity's id, but
 * {@code Entity Manager} s also provide methods for tagging objects with
 * different types.
 */
public interface ECSystem extends NotNullValidator {

	public boolean validate(int entityId);
	public void process(float delta);
	
	/**
	 * The method contains all the logic, applied to an Entity
	 * during the execution of this system
	 * @param entityId Identifier of the Entity being processed
	 */
	default void process(int entityId, float delta) {
		if (validate(entityId)) {
			process(delta);
		}
	}
	
	default boolean areNotNull(Object... fields) {
		return NotNullValidator.super.checkNotNull(fields);
	}
	
}
