package com.github.tavi.plague.ecs.renderable;

import com.badlogic.gdx.math.Vector2;

public interface TextureMeta {
	public static final Vector2 NORTH= new Vector2(0.5f, 0.85f);
	public static final Vector2 SOUTH = new Vector2(0.5f, 0.15f);
	public static final Vector2 NORTH_EAST = new Vector2(0.85f, 0.85f);
	public static final Vector2 NORTH_WEST = new Vector2(0.15f, 0.85f);
	
	public String path();
	public boolean flipX();
	public boolean flipY();
	public boolean flipX(boolean value);
	public boolean flipY(boolean value);
	public Vector2 origin();
	public Vector2 size();
	public Vector2 srcOrigin();
	public Vector2 srcSize();
	public void setFrame(int frame);

	default float originX() {
		return origin().x;
	}
	default float  originY() {
		return origin().y;
	}
	default int srcX() {
		return (int) srcOrigin().x;
	}
	default int srcY() {
		return (int) srcOrigin().y;
	}
	default int srcWidth() {
		return (int) srcSize().x;
	}
	default int srcHeight() {
		return (int) srcSize().y;
	}

}
