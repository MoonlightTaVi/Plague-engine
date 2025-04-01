package com.github.tavi.plague;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.sciacallo.Renderer;
import com.github.tavi.plague.sciacallo.components.TextureMeta;
import com.github.tavi.plague.shared.Assets;
import com.github.tavi.plague.shared.Components;
import com.github.tavi.plague.shared.Entities;
import com.github.tavi.plague.shared.components.Transform;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private Renderer renderer = new Renderer();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	int id = entities.create();
        Transform t = components.register(id, Transform.class, new Transform());
        t.x = 50;
        t.y = 50;
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_waist_0.png", 0.5f, 0f));
        components.register(id, Vector3.class, new Vector3(5, 5, 0)); // Currently only rotation
        
        id = entities.create();
        t = components.register(id, Transform.class, new Transform());
        t.x = 60;
        t.y = 70;
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", 0.5f, 0f));
        components.register(id, Vector3.class, new Vector3(5, 5, 0)); // Currently only rotation
        
    }

    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//components.get(0, Transform.class).x += 5 * delta;
        
        assets.batch().begin();
        
        entities.stream().forEach(id -> renderer.process(id));
        //renderer.process(0);
        
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