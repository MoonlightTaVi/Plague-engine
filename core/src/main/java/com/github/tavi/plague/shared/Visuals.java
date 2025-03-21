package com.github.tavi.plague.shared;

import com.github.tavi.plague.sciacallo.Spectato;

/**
 * A main class to work with textures and other visuals
 */
public interface Visuals {
	
	/**
	 * Returns the singleton instance of the currently used Visuals implementation.
	 * As of v1.0.0 ("Sciacallo" version), Spectato is used
	 * @see Spectato
	 * @return Singleton instance of Visuals implementation
	 */
	public static Visuals get() {
		return Spectato.get();
	}
	
	/**
	 * Loads a texture to the RAM
	 * @param internalTexturePath Path to a texture inside the "assets/" directory
	 */
	public void load(String internalTexturePath);
	
}
