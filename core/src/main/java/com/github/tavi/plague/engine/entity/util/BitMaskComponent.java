package com.github.tavi.plague.engine.entity.util;

import java.util.*;

public class BitMaskComponent implements BitMask {
	private static final TreeSet<String> componentNames = new TreeSet<>();
	
	public static void registerComponents(Class<?>... componentClasses) {
		for (Class<?> componentClass : componentClasses) {
			componentNames.add(componentClass.getName());
		}
	}
	
	public static int getMaskFor(Class<?>... involvedComponents) {
		Set<String> componentClassNames = new HashSet<>();
		for (Class<?> involvedComponent : involvedComponents) {
			componentClassNames.add(involvedComponent.getName());
		}
		int mask = 0;
		int i = 0;
		for (String registeredComponent : componentNames) {
			if (componentClassNames.contains(registeredComponent)) {
				mask |= 1 << i;
			}
			i++;
		}
		return mask;
	}
	
	private int value = -1;
	private final BitMask bitMaskUser;
	
	public BitMaskComponent(BitMask bitMaskUser) {
		this.bitMaskUser = bitMaskUser;
	}

	@Override
	public int value() {
		return value != -1 ? value : getMaskFor(getInvolvedComponents());
	}

	@Override
	public Class<?>[] getInvolvedComponents() {
		return bitMaskUser.getInvolvedComponents();
	}
	
}
