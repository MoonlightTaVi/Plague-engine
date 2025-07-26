package com.github.tavi.plague.engine.system;

import java.util.*;

import com.github.tavi.plague.engine.PubSubDispatcher;
import com.github.tavi.plague.engine.entity.Entity;

public class SystemOrchestra implements AbstractProcessor, PubSubDispatcher<Entity> {
	private List<ECSystemDispatcher> systems = new ArrayList<>();

	public SystemOrchestra(ECSystemDispatcher... systems) {
		this.systems.addAll(List.of(systems));
	}
	
	@Override
	public boolean subscribe(Entity entity) {
		boolean success = false;
		for (ECSystemDispatcher system : systems) {
			if (system.test(entity.getMask())) {
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
