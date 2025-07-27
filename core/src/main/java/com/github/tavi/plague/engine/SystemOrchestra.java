package com.github.tavi.plague.engine;

import java.util.*;

import com.github.tavi.plague.engine.shared.Entity;
import com.github.tavi.plague.engine.system.AbstractProcessor;
import com.github.tavi.plague.engine.system.ECSystemDispatcher;
import com.github.tavi.plague.util.PubSubDispatcher;

public class SystemOrchestra implements AbstractProcessor, PubSubDispatcher<Entity> {
	private List<ECSystemDispatcher> systems = new ArrayList<>();

	public SystemOrchestra(BaseEntityProcessor... processors) {
		for (BaseEntityProcessor processor : processors) {
			ECSystemDispatcher system = new ECSystemDispatcher(processor);
			systems.add(system);
		}
	}
	
	public SystemOrchestra(ECSystemDispatcher... systems) {
		for (ECSystemDispatcher system : systems) {
			this.systems.add(system);
		}
	}
	
	public boolean subscribe(int entityId) {
		return subscribe(new Entity(entityId));
	}
	
	@Override
	public boolean subscribe(Entity entity) {
		boolean success = false;
		for (ECSystemDispatcher system : systems) {
			if (system.test(entity.value())) {
				success = success || system.subscribe(entity.id);
			}
		}
		return success;
	}
	
	@Override
	public boolean unsubscribe(Entity entity) {
		boolean success = false;
		for (ECSystemDispatcher system : systems) {
			success = success || system.unsubscribe(entity.id);
		}
		return success;
	}
	
	@Override
	public void process(float delta) {
		for (ECSystemDispatcher system : systems) {
			system.process(delta);
		}
	}
	
}
