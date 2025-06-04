package com.github.tavi.plague.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.ecs.spatial.renderable.*;
import com.github.tavi.plague.ecs.spatial.renderable.ik.*;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private VisibleSystem renderer = new Renderer();
	private VisibleSystem rotationSystem = new RotationSystem();
	private VisibleSystem limbMovement = new LimbMovementSystem();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	LimbMovementStrategy.setupStrategies();
    	
    	int id = entities.create();
        Transform t = components.register(id, Transform.class, new Transform());
        components.register(id, MovementState.class, MovementState.DANCING);
        t.x = 50;
        t.y = 50;
        Skelly skelly = components.register(id, new Skelly(Skelly.Type.HUMAN));
        Arm spine = skelly.growArm(Arm.Type.SPINE, 2);
        
        id = spine.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_waist_0.png", 0.5f, 0f));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 18));
        
        id = spine.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", 0.5f, 0f));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 29));
        
        
        
        
    }

    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0, 0, 0, 1);
        
        assets.batch().begin();
        
        entities.stream()
        .peek(id -> rotationSystem.process(id, delta))
        .peek(id -> limbMovement.process(id, delta))
        .forEach(id -> renderer.process(id, delta));
        
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