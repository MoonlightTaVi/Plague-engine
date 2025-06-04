package com.github.tavi.plague.ecs.spatial.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.github.tavi.plague.util.io.Assets;

/**
 * The record contains all the static information needed in order to render a Texture on the screen. <br>
 * It does not contain the Texture itself.
 */
public record TextureMeta(String path, float originX, float originY, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight) {
	public static Vector2 SOUTH = new Vector2(0.5f, 0.15f);
	
	/**
	 * Create a simple record on the Texture (centered at {@code (x:0;y:0)})
	 * @param path Internal path to the texture
	 */
	public TextureMeta(String path) {
		this(path, 0, 0);
	}
	
	/**
	 * Create a record on the Texture, specifying its center
	 * @param path Internal path to the texture
	 * @param offset A vector with values from 0 to 1,
	 * corresponding to where the texture should be centered by its width and height
	 */
	public TextureMeta(String path, Vector2 offset) {
		this(path, texture(path).getWidth() * offset.x, texture(path).getHeight() * offset.y, texture(path).getWidth(), texture(path).getHeight(), 0, 0, texture(path).getWidth(), texture(path).getHeight());
	}
	
	/**
	 * Create a record on the Texture, specifying its center
	 * @param path Internal path to the texture
	 * @param offsetX A value from 0 to 1, corresponding to where the texture should be centered by its width
	 * @param offsetY A value from 0 to 1, corresponding to where the texture should be centered by its height
	 */
	public TextureMeta(String path, float offsetX, float offsetY) {
		this(path, texture(path).getWidth() * offsetX, texture(path).getHeight() * offsetY, texture(path).getWidth(), texture(path).getHeight(), 0, 0, texture(path).getWidth(), texture(path).getHeight());
	}
	
	/**
	 * Get the texture by the path (if loaded)
	 * @param path Internal path to the texture
	 * @return The texture corresponding to this path
	 */
	private static Texture texture(String path) {
		return Assets.get().texture(path);
	}
}
