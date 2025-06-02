package com.github.tavi.plague.ecs.spatial.renderable;

import com.github.tavi.plague.ecs.System;

/**
 * A marker interface to show that this processor is active only
 * on Entities rendered on the screen
 */
public interface VisibleSystem extends System {
	
	public boolean isVisible(int id);
	
}
