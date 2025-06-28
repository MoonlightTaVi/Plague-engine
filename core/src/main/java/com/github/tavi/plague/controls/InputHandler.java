package com.github.tavi.plague.controls;

import com.github.tavi.plague.ecs.spatial.LookVector;

public class InputHandler {
	private WasdaHandler wasd = new WasdaHandler();
	private LookVector lookingAt;
	
	public InputHandler(LookVector playerLookingAtComponent) {
		lookingAt = playerLookingAtComponent;
	}
	
	public void handleInput(float delta) {
		lookingAt.current().set(
				wasd.getDirection(),
				0
				);
	}

}
