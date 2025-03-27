package com.github.tavi.plague;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.sciacallo.Catti;
import com.github.tavi.plague.sciacallo.Renderer;
import com.github.tavi.plague.sciacallo.components.TextureMeta;
import com.github.tavi.plague.shared.Assets;
import com.github.tavi.plague.shared.components.Transform;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Catti components = Catti.get();
	private Renderer renderer = new Renderer();
	private Assets assets = Assets.get();
	// <--- TEMPORARY
	
    @Override
    public void show() {
        Transform t = components.register(0, Transform.class, new Transform());
        //t.x = 50;
        //t.y = 50;
        components.register(0, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", 0.5f, 0f));
    }

    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//components.get(0, Transform.class).x += 5 * delta;
        
        assets.batch().begin();
        
        renderer.process(0);
        
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
        assets.dispose();
    }
}