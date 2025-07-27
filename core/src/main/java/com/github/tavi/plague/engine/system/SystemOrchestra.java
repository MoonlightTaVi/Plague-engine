package com.github.tavi.plague.engine.system;

import java.util.*;

import com.github.tavi.plague.engine.PubSubDispatcher;
import com.github.tavi.plague.engine.entity.Entity;
import com.github.tavi.plague.engine.entity.util.MaskResolver;

public class SystemOrchestra implements AbstractProcessor, PubSubDispatcher<Entity> {
	private List<ECSystemDispatcher> systems = new ArrayList<>();

	public SystemOrchestra(BaseEntityProcessor... processors) {
		for (BaseEntityProcessor processor : processors) {
			ECSystemDispatcher system = new ECSystemDispatcher(processor);
			systems.add(system);
			MaskResolver.register(system.getInvolvedComponents());
		}
	}
	
	public SystemOrchestra(ECSystemDispatcher... systems) {
		for (ECSystemDispatcher system : systems) {
			this.systems.add(system);
			MaskResolver.register(system.getInvolvedComponents());
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
