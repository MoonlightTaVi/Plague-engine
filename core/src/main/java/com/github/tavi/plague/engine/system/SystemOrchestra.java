package com.github.tavi.plague.engine.system;

import java.util.*;

public class SystemOrchestra {
	private List<ECSystem> systems = new ArrayList<>();

	public void registerSystem(ECSystem system) {
		systems.add(system);
	}
	
	public void addSubscriber(int entityId, int entityMask) {
		for (ECSystem system : systems) {
			int systemMask = system.getSystemMask();
			if ((systemMask & entityMask) == systemMask) {
				system.subscribe(entityId);
			}
		}
	}
	
	public void process(float delta) {
		for (ECSystem system : systems) {
			system.process(delta);
		}
	}
	
}
