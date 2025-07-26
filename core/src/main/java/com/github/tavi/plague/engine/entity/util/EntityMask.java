package com.github.tavi.plague.engine.entity.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EntityMask {
	private TreeSet<String> componentNames = new TreeSet<>();
	private String[] namesArray = null;
	
	private final IllegalStateException illegalState = new IllegalStateException(
			String.format(
					"build() must be called on %s after any signature change.",
					this.getClass().getSimpleName()
					)
			);
	

	public void register(Object component) {
		namesArray = null;
		componentNames.add(component.getClass().getName());
	}
	
	public int register(Object... components) {
		namesArray = null;
		for (Object component : components) {
			componentNames.add(component.getClass().getName());
		}
		build();
		return getMask(components);
	}
	
	public void build() {
		namesArray = componentNames.toArray(String[]::new);
	}

	public int getMask(Object... components) {
		Set<String> registeredNames = Arrays.stream(components)
				.map(o -> o.getClass().getName())
				.collect(Collectors.toSet());
		if (namesArray == null) {
			throw illegalState;
		}
		int mask = 0;
		for (int i = 0; i < namesArray.length; i++) {
			if (registeredNames.contains(namesArray[i])) {
				mask |= 1 << i;
			}
		}
		return mask;
	}
	
}
