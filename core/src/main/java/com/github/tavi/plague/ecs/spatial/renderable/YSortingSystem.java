package com.github.tavi.plague.ecs.spatial.renderable;

import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.SortingSystem;
import com.github.tavi.plague.ecs.spatial.Transform;

public class YSortingSystem implements SortingSystem {
	private Components components = Components.get();
	
	@Override
	public int process(int idA, int idB) {
		Transform transformA = null;
		Transform transformB = null;
		if ((transformA = components.get(idA, Transform.class)) == null ||
				(transformB = components.get(idB, Transform.class)) == null
				) {
			return 0;
		}
		/*ECSystem.out.println(idA + " " + idB);
		ECSystem.out.println(transformA.worldPosition.y + " " + transformB.worldPosition.y);
		ECSystem.out.println(Float.compare(transformB.worldPosition.y, transformA.worldPosition.y));
		ECSystem.out.println(transformA.worldPosition);*/
		return Float.compare(transformB.worldPosition.y, transformA.worldPosition.y);
	}

}
