package com.github.tavi.plague.game.controls;

import com.github.tavi.plague.ecs.behaviour.*;

public class InputHandler {
	private WasdaHandler wasd = new WasdaHandler();
	private LookDirection lookingAt;
	
	public InputHandler(LookDirection playerLookingAtComponent) {
		lookingAt = playerLookingAtComponent;
	}
	
	public void process(float delta) {
		lookingAt.current().set(
				wasd.getDirection(),
				0
				);
	}

}
