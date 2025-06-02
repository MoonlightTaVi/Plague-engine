package com.github.tavi.plague.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.Entities;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.Renderer;
import com.github.tavi.plague.ecs.spatial.renderable.TextureMeta;
import com.github.tavi.plague.ecs.spatial.renderable.VisibleSystem;
import com.github.tavi.plague.ecs.spatial.renderable.ik.Arm;
import com.github.tavi.plague.ecs.spatial.renderable.ik.FABRIK;
import com.github.tavi.plague.ecs.spatial.renderable.ik.Skelly;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private VisibleSystem renderer = new Renderer();
	private VisibleSystem ik = new FABRIK();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	int id = entities.create();
        Transform t = components.register(id, Transform.class, new Transform());
        t.x = 50;
        t.y = 50;
        Skelly skelly = components.register(id, new Skelly());
        skelly.spine = new Arm(2);
        
        id = skelly.spine.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_waist_0.png", 0.5f, 0f));
        components.register(id, Vector3.class, new Vector3(0, 18, 0));
        
        id = skelly.spine.push(entities.create());
        //t = components.register(id, Transform.class, new Transform());
        //t.x = 60;
        //t.y = 70;
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", 0.5f, 0f));
        components.register(id, Vector3.class, new Vector3(0, 29, 0));
        
        
        
        
    }

    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//components.get(0, Transform.class).x += 5 * delta;
        
        assets.batch().begin();
        
        entities.stream().peek(id -> ik.process(id)).forEach(id -> renderer.process(id));;
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