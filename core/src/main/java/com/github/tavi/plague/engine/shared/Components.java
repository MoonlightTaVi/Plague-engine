package com.github.tavi.plague.engine.shared;

import java.util.Collection;

import com.github.tavi.plague.engine.silva.ComponentsSpread;

public interface Components {
	
	public static Components get() {
		return ComponentsSpread.get();
	}

	public void addComponents(int entityId, Object... components);
	public void removeComponents(int entityId, Object... components);
	public Collection<Object> getComponents(int entityId);
	public <T> T getComponent(int entityId, Class<T> componentClass);
	
}
