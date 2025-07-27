package com.github.tavi.plague.engine.system;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.util.*;

public abstract class BaseEntityProcessor implements EntityProcessor, BitMask {
	private Components components = Components.get();
	private BitMask bitMask = new BitMaskComponent(this);
	private Class<?>[] involvedComponents;
	private boolean isActive = true;
	
	public BaseEntityProcessor(Class<?>... involvedComponents) {
		this.involvedComponents = involvedComponents;
	}
	
	@Override
	public void enable() {
		isActive = true;
	}
	@Override
	public void disable() {
		isActive = false;
	}
	@Override
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public int value() {
		return bitMask.value();
	}
	@Override
	public Class<?>[] getInvolvedComponents() {
		return involvedComponents;
	}
	
	public <T> T getComponent(int entityId, Class<T> componentClass) {
		return components.getComponent(entityId, componentClass);
	}
	
	public abstract void process(int entityId, float delta);
}
