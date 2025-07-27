package com.github.tavi.plague.engine.system;

public interface EntityProcessor {

	public void enable();
	public void disable();
	public boolean isActive();
	public void process(int entityId, float delta);
	
}
