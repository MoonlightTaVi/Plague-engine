package com.github.tavi.plague.sciacallo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.github.tavi.plague.shared.Visuals;

/**
 * The "Sciacallo" version of the Visuals
 * @see Visuals
 */
public class Spectato implements Visuals {

	private static volatile Spectato INSTANCE = null;
	
	/**
	 * Get a singleton of Spectato
	 * @return Singleton instance of Spectato to store in some objects field afterwards
	 */
	public static Spectato get() {
		if (INSTANCE == null) {
			synchronized (Spectato.class) {
				INSTANCE = new Spectato();
			}
		}
		return INSTANCE;
	}
	
	private Spectato() { }
	
	
	private Map<String, Texture> textures = new HashMap<>();

	@Override
	public void load(String internalTexturePath) {
		Texture texture = new Texture(Gdx.files.internal(internalTexturePath));
	}
	
	

}
