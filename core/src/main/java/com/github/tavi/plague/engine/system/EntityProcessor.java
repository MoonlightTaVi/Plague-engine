package com.github.tavi.plague.engine.system;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.util.MaskResolver;

public abstract class EntityProcessor {
	private Components components = Components.get();
	private int mask = 0;
	private Class<?>[] involvedComponents;
	protected boolean isActive = true;
	
	public EntityProcessor(Class<?>... involvedComponents) {
		this.involvedComponents = involvedComponents;
		updateMask();
	}
	
	protected void enable() {
		isActive = true;
	}
	protected void disable() {
		isActive = false;
	}
	public boolean isActive() {
		return isActive;
	}

	public boolean test(int mask) {
		return (this.mask | mask) == this.mask;
	}
	public void updateMask() {
		mask = MaskResolver.getMask(involvedComponents);
	}
	
	public <T> T getComponent(int entityId, Class<T> componentClass) {
		return components.getComponent(entityId, componentClass);
	}
	
	public abstract void process(int entityId, float delta);
}
