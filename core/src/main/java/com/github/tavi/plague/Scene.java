package com.github.tavi.plague;

import com.badlogic.gdx.Screen;
import com.github.tavi.plague.shared.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	private Assets assets = Assets.get();
	
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
    	if (!assets.update()) {
    		System.out.print((int) (assets.getProgress() * 100));
    		System.out.println("%");
    	}
        
        assets.batch().begin();
        
        assets.batch().draw(assets.texture("textures/real-male/male_torso_0.png"), 20, 20);
        
        assets.batch().end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}