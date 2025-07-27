package com.github.tavi.plague.engine.silva;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import com.github.tavi.plague.engine.shared.Components;

public class ComponentsSpread implements Components {
	private static ComponentsSpread instance = null;
	private static ReentrantLock lock = new ReentrantLock();
	public static ComponentsSpread get() {
		try {
			lock.lock();
			if (instance == null) {
				instance = new ComponentsSpread();
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}
	
	
	private Map<Integer, Set<Object>> componentsSpread = new HashMap<>();

	@Override
	public void addComponents(int entityId, Object... components) {
		getComponents(entityId).addAll(List.of(components));
	}

	@Override
	public void removeComponents(int entityId, Object... components) {
		getComponents(entityId).removeAll(List.of(components));
	}

	@Override
	public Collection<Object> getComponents(int entityId) {
		if (!componentsSpread.containsKey(entityId)) {
			componentsSpread.put(entityId, new HashSet<>());
		}
		return componentsSpread.get(entityId);
	}

	@Override
	public <T> T getComponent(int entityId, Class<T> componentClass) {
		T desiredComponent = null;
		for (Object component : getComponents(entityId)) {
			if (componentClass.isInstance(component)) {
				desiredComponent = componentClass.cast(component);
				break;
			}
		}
		return desiredComponent;
	}

}
