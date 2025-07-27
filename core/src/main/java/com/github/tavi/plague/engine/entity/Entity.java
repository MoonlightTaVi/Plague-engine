package com.github.tavi.plague.engine.entity;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.util.BitMask;
import com.github.tavi.plague.engine.entity.util.BitMaskComponent;

public class Entity implements BitMask {
	private final Components components = Components.get();
	public int id = -1;
	private BitMaskComponent bitMask = new BitMaskComponent(this);
	
	public Entity(int entityId) {
		id = entityId;
	}
	
	public Entity(int entityId, Object... components) {
		id = entityId;
		this.components.addComponents(id, components);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public <T> T getComponent(Class<T> componentClass) {
		return components.getComponent(id, componentClass);
	}
	@Override
	public Class<?>[] getInvolvedComponents() {
		return components.getComponents(id)
				.stream()
				.map(Object::getClass)
				.toArray(Class<?>[]::new);
	}
	@Override
	public int value() {
		return bitMask.value();
	}
	
}
