package com.github.tavi.plague.util.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The main class to work with textures and other assets
 */
public interface Assets {
	
	/**
	 * Returns the singleton instance of the currently used Assets implementation.
	 * As of v1.0.0 ("Sciacallo" version), Requisitum is used
	 * @see Requisitum
	 * @return Singleton instance of Assets implementation
	 */
	public static Assets get() {
		return Requisitum.get();
	}
	
	/**
	 * Returns a cached texture
	 * @param internalTexturePath Path to a texture inside the "assets/textures" directory
	 */
	public Texture texture(String internalTexturePath);
	
	/**
	 * Returns the batch, used for rendering graphics
	 * @return SpriteBatch for rendering
	 * @see SpriteBatch
	 */
	public SpriteBatch batch();
	
	/**
	 * Upload a single file from the "assets/" directory at a time.
	 * Call it inside the "render()" method.
	 * @return true if finished loading, false otherwise
	 */
	public boolean update();
	
	/**
	 * Get the percentage of uploaded assets
	 * @return A float value from 0 to 1, representing the percentage of uploaded assets
	 */
	public float getProgress();
	
	/**
	 * Clear all the assets from the cache
	 */
	public void dispose();
	
}
