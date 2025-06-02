package com.github.tavi.plague.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.tavi.plague.util.io.Assets;

public class LoadingScreen implements Screen {
	
	private Game game;
	private Screen nextScreen;
	
	private Texture background = null;
	private SpriteBatch batch = new SpriteBatch();
	private Assets assets = Assets.get();
	
	public LoadingScreen(Game game, Screen nextScreen) {
		this.game = game;
		this.nextScreen = nextScreen;
	}

	@Override
	public void show() {
		background = new Texture(Gdx.files.internal("Survivor.png"));
	}

	@Override
	public void render(float delta) {
		// If put the .draw call in show(), not in render(), it is flickering for some reason
		
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		// Switch to the next screen after loading assets
		if (assets.update()) {
			game.setScreen(nextScreen);
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
	}

}
