package com.github.tavi.plague.ecs.renderable;

import java.util.Comparator;

import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.Transform;

public class IsometricComparator implements Comparator<Integer> {
	private Components components = Components.get();

	@Override
	public int compare(Integer o1, Integer o2) {
		float f1 = components.get(o1, Transform.class).worldPosition.y;
		float f2 = components.get(o2, Transform.class).worldPosition.y;
		return Float.compare(f2, f1);
	}

}
