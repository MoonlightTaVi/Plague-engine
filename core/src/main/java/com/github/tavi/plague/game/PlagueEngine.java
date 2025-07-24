package com.github.tavi.plague.game;

import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.engine.EntityMask;

public class PlagueEngine {
	private Components components = Components.get();
	private TreeOfEntities entities = TreeOfEntities.get();
	private EntityMask mask = new EntityMask();
	
	public void register(int entityId, Object... entityComponents) {
		entities.createIfAbsent(entityId);
		for (Object component : entityComponents) {
			components.register(entityId, component);
			mask.register(component);
		}
		int oldMask = entities.getMask(entityId);
		int newMask = mask.getMask(entityComponents);
		entities.setMask(entityId, oldMask | newMask);
	}
	
	public void unregister(int entityId, Object... entityComponents) {
		for (Object component : entityComponents) {
			//components.unregister(entityId, component);
		}
		int oldMask = entities.getMask(entityId);
		int invertedMask = ~mask.getMask(entityComponents);
		entities.setMask(entityId, oldMask & invertedMask);
	}
	
	
}
