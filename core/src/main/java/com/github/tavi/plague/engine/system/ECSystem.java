package com.github.tavi.plague.engine.system;

public interface ECSystem {

	public int getSystemMask();
	public void subscribe(int entityId);
	public void process(float delta);
	
}
