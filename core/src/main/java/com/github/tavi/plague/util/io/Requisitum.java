package com.github.tavi.plague.util.io;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.tavi.plague.util.logging.Plogger;

/**
 * The "Sciacallo" version of the Assets. <br>
 * Scans for the asset files at the instantiation.
 * @see Assets
 */
public class Requisitum implements Assets {

	private static volatile Requisitum INSTANCE = null;
	
	/**
	 * Get a singleton of Requisitum
	 * @return Singleton instance of Requisitum to store in some objects field afterwards
	 */
	public static Requisitum get() {
		if (INSTANCE == null) {
			synchronized (Requisitum.class) {
				if (INSTANCE == null) {
					INSTANCE = new Requisitum();
				}
			}
		}
		return INSTANCE;
	}
	
	
	private Plogger log = Plogger.get();
	
	private SpriteBatch batch = new SpriteBatch();
	private AssetManager assets = new AssetManager();
	private TextureParameter textureParam = new TextureParameter();
	
	// If something is corrupted, we ignore it in the future
	private Set<String> problematicAssets = new HashSet<>();
	
	/**
	 * Instantiates a "Requisitum" {@link Assets} implementation, which scans the "assets/" directory for all the available asset files
	 * (but does not upload them; {@link #update()} is used for uploading).
	 */
	private Requisitum() {
		textureParam.minFilter = Texture.TextureFilter.Nearest;
		
		iterate(Gdx.files.internal("textures/"));
	}
	
	/**
	 * Recursively iterate the directory, loading all supported asset files
	 * @param directory The folder to scan for assets
	 */
	private void iterate(FileHandle directory) {
		
		FileHandle[] items = directory.list();
		
		for (FileHandle item : items) {
			
			if (item.extension().equals("png")) {
				log.info(this, "Found texture:", item.path());
				assets.load(item.path(), Texture.class, textureParam);
			} else if (item.isDirectory()) {
				iterate(item);
			}
			
		}
	}

	@Override
	public Texture texture(String internalTexturePath) {
		if (problematicAssets.contains(internalTexturePath)) {
			return null;
		}
		
		if (assets.isLoaded(internalTexturePath)) {
			return assets.get(internalTexturePath, Texture.class);
		}
		
		problematicAssets.add(internalTexturePath);
		
		log.error(this, new FileNotFoundException("Texture does not exist or corrupted"), "Path to the texture:", internalTexturePath);
		
		return null;
	}

	@Override
	public SpriteBatch batch() {
		return batch;
	}
	
	@Override
	public boolean update() {
		return assets.update(17);
	}

	@Override
	public float getProgress() {
		return assets.getProgress();
	}

	@Override
	public void dispose() {
		assets.dispose();
	}
	
	

}
