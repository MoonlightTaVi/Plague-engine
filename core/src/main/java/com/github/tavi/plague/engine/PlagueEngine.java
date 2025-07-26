package com.github.tavi.plague.engine;

import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.entity.TreeOfEntities;
import com.github.tavi.plague.engine.entity.util.EntityMask;

public class PlagueEngine {
	private Components components = Components.get();
	private TreeOfEntities entities = TreeOfEntities.get();
	private EntityMask mask = new EntityMask();
	
	public void register(int entityId, Object... entityComponents) {
		if (!entities.idExists(entityId)) {
			throw new IllegalArgumentException("Entity does not exist, entityId: " + entityId);
		}
		for (Object component : entityComponents) {
			components.addComponents(entityId, component);
			mask.register(component);
		}
	}
	
	public void unregister(int entityId, Object... entityComponents) {
		for (Object component : entityComponents) {
			components.removeComponents(entityId, component);
		}
	}
	
	
}
