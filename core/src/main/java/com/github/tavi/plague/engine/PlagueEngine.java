package com.github.tavi.plague.engine;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.TreeOfEntities;
import com.github.tavi.plague.engine.entity.util.MaskResolver;

public class PlagueEngine {
	private Components components = Components.get();
	private TreeOfEntities entities = TreeOfEntities.get();
	
	public void register(int entityId, Object... entityComponents) {
		if (!entities.idExists(entityId)) {
			throw new IllegalArgumentException("Entity does not exist, entityId: " + entityId);
		}
		for (Object component : entityComponents) {
			components.addComponents(entityId, component);
			MaskResolver.register(component.getClass());
		}
	}
	
	public void unregister(int entityId, Object... entityComponents) {
		for (Object component : entityComponents) {
			components.removeComponents(entityId, component);
		}
	}
	
	
}
