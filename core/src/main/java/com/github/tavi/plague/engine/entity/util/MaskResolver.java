package com.github.tavi.plague.engine.entity.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MaskResolver {
	private static TreeSet<String> componentNames = new TreeSet<>();
	private static String[] namesArray = null;
	

	public static void register(Class<?> component) {
		namesArray = null;
		componentNames.add(component.getName());
	}
	
	public static void register(Class<?>... components) {
		namesArray = null;
		for (Class<?> component : components) {
			componentNames.add(component.getName());
		}
	}
	
	public static void build() {
		namesArray = componentNames.toArray(String[]::new);
	}
	
	public static int getMask(Object... components) {
		return getMask(
				Arrays.stream(components)
				.map(Object::getClass)
				.toArray(Class<?>[]::new)
				);
	}

	public static int getMask(Class<?>... componentClasses) {
		Set<String> registeredNames = Arrays.stream(componentClasses)
				.map(o -> o.getClass().getName())
				.collect(Collectors.toSet());
		
		if (namesArray == null) {
			throw new IllegalStateException("build() must be called after each update before proceeding.");
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
