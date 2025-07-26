package com.github.tavi.plague.engine.entity;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.util.MaskResolver;

public class Entity {
	private final Components components = Components.get();
	public int id = -1;
	public void setId(int id) {
		this.id = id;
	}
	public <T> T getComponent(Class<T> componentClass) {
		return components.getComponent(id, componentClass);
	}
	public int getMask() {
		return MaskResolver.getMask(components.getComponents(id));
	}
	
}
