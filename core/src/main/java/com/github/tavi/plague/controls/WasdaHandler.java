package com.github.tavi.plague.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class WasdaHandler {
	private Vector2 direction = new Vector2(0, 0);

	/**
	 * Scans for WASD input from keyboard, mapping
	 * W and S keys to the Y-axis and A and D to the X-axis
	 * of a Vector2.
	 * @return Normalized Vector2, that represents two-dimensional
	 * direction of WASD keys pressed.
	 */
	public Vector2 getDirection() {
		direction.x = keyToInt(Input.Keys.D) - keyToInt(Input.Keys.A);
		direction.y = keyToInt(Input.Keys.W) - keyToInt(Input.Keys.S);
		return direction.nor();
	}
	
	/**
	 * Converts key-pressed event to an Integer value,
	 * corresponding to an axis of a Vector2.
	 * @param keyCode Enum from Gdx.Input.Keys.
	 * @return 1 if key pressed, 0 otherwise.
	 */
	private int keyToInt(int keyCode) {
		return Boolean.compare(
				Gdx.input.isKeyPressed(keyCode),
				false
				);
	}
	
}
