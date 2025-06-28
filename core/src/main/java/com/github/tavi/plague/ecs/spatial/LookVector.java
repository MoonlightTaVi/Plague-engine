package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;

/**
 * A vector that represents a character's (both playable
 * and non-playable) "looking at"/"facing at" direction.
 * Can be helpful for Line-of-Sight / Field-of-View
 * related algorithms, as well as for rotating a character's
 * model and all its pieces simultaneously. <br><br>
 * Looking rotation angle may be calculated by comparing
 * {@code Vector3 original} (must be static, i.e. never change)
 * and {@code Vector3 current}.
 */
public record LookVector(Vector3 original, Vector3 current) {

	/**
	 * Constructs an original LookVector for an Entity,
	 * based on its initial "looking at" direction.
	 * @param original "Where is this Entity facing at" originally,
	 * relative to its position.
	 */
	public LookVector(Vector3 original) {
		this(new Vector3(original), new Vector3(original));
	}

	/**
	 * A short-cut for the primary constructor.
	 * @param x X-coordinate of the {@code Vector3 original}.
	 * @param y Y-coordinate of the {@code Vector3 original}.
	 * @param z Z-coordinate of the {@code Vector3 original}.
	 * @see #LookVector(Vector3)
	 */
	public LookVector(float x, float y, float z) {
		this(new Vector3(x, y, z));
	}
	
}
