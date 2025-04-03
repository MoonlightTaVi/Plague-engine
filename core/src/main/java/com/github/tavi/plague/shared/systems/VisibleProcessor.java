package com.github.tavi.plague.shared.systems;

/**
 * A marker interface to show that this processor is active only
 * on Entities rendered on the screen
 */
public interface VisibleProcessor extends EntityProcessor {
	
	public boolean isVisible(int id);
	
}
