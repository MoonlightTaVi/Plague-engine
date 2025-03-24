package com.github.tavi.plague.shared;

public interface Components<ENTITY> {

	public <COMPONENT> COMPONENT register(ENTITY entity, Class<COMPONENT> componentType, COMPONENT component);
	public <COMPONENT> COMPONENT get(ENTITY entity, Class<COMPONENT> componentType);
	
}
