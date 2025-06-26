package com.github.tavi.plague.ecs.spatial.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.github.tavi.plague.util.io.Assets;

/**
 * The record contains all the static information needed in order to render a Texture on the screen. <br>
 * It does not contain the Texture itself.
 */
public class SpriteSheet implements TextureMeta {
	Texture texture = null;
	int frames = 1;
	int frame = 0;
	String path;
	final Vector2 origin = new Vector2(0, 0);
	final Vector2 dimensions = new Vector2(0, 0);
	final Vector2 sourceOrigin = new Vector2(0, 0);
	final Vector2 sourceSize = new Vector2(0, 0);
	final Vector2 frameOrigin = new Vector2(0, 0);
	final Vector2 frameSize = new Vector2(0, 0);
	boolean originalFlipX = false;
	boolean originalFlipY = false;
	boolean flipX = false;
	boolean flipY = false;
	
	public SpriteSheet() {
	}
	
	public SpriteSheet(boolean flipX) {
		originalFlipX = flipX;
	}
	
	public SpriteSheet(boolean flipX, boolean flipY) {
		originalFlipX = flipX;
		originalFlipY = flipY;
	}
	
	public SpriteSheet fromImage(String path) {
		this.path = path;
		dimensions.set(texture(path).getWidth(), texture(path).getHeight());
		sourceSize.set(dimensions.x, dimensions.y);
		frameSize.set(dimensions.x, dimensions.y);
		return this;
	}
	
	public SpriteSheet centeredAt(float x, float y) {
		origin.set(x, y);
		return this;
	}
	
	public SpriteSheet centeredAt(Vector2 relativeOrigin) {
		this.origin.set(relativeOrigin).scl(dimensions);
		return this;
	}
	
	public SpriteSheet withFrames(int numberOfFrames) {
		frames = numberOfFrames;
		return this;
	}
	
	public SpriteSheet build() {
		if (frames > 0 && sourceSize.len2() > 0) {
			frameSize.set(sourceSize.x / frames, sourceSize.y);
		}
		return this;
	}
	
	@Override
	public boolean flipX() {
		return originalFlipX && !flipX;
	}
	
	@Override
	public boolean flipY() {
		return originalFlipY && !flipY;
	}
	
	@Override
	public boolean flipX(boolean value) {
		flipX = value;
		return originalFlipX && !flipX;
	}
	
	@Override
	public boolean flipY(boolean value) {
		flipY = value;
		return originalFlipY && !flipY;
	}

	@Override
	public String path() {
		return path;
	}

	@Override
	public Vector2 origin() {
		return origin;
	}

	@Override
	public Vector2 size() {
		return dimensions;
	}

	@Override
	public Vector2 srcOrigin() {
		return frameOrigin;
	}

	@Override
	public Vector2 srcSize() {
		return frameSize;
	}

	@Override
	public void setFrame(int frame) {
		this.frame = frame & frames;
		frameOrigin.set(frameSize.x * this.frame, frameOrigin.y);
	}
	
	/**
	 * Get the texture by the path (if loaded).
	 * @param path Internal path to the texture.
	 * @return The texture corresponding to this path.
	 */
	private static Texture texture(String path) {
		return Assets.get().texture(path);
	}
}
