package com.github.tavi.plague.ecs.spatial;

import com.badlogic.gdx.math.Vector3;

/**
 * Main component, representing the Entity's position
 * in the world. Used by all spatial EC systems.
 * @see #worldPosition
 * @see #rotatedOffset
 */
public class Transform {
	/** Base world position. */
	public Vector3 worldPosition = new Vector3();
	/** Original local offset (added to worldPosition if rotation is 0f). */
	public Vector3 originalOffset = new Vector3();
	/** Local offset after being rotated (added to worldPosition). */
	public Vector3 rotatedOffset = new Vector3();
	
	public Transform() { }
	
	/**
	 * Constructs the Transform component, setting
	 * its initial offset from its base origin.
	 * @param offsetX
	 * @param offsetY
	 * @param offsetZ
	 * @see #originalOffset
	 */
	public Transform(float offsetX, float offsetY, float offsetZ) {
		originalOffset.set(offsetX, offsetY, offsetZ);
	}
	
	
	
}
