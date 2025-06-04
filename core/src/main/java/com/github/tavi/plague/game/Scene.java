package com.github.tavi.plague.game;

import com.badlogic.gdx.Gdx;
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
	private VisibleSystem hierarchySystem = new VisibleHierarchySystem();
	private VisibleSystem rotationSystem = new TextureRotationSystem();
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
        t.setWorldPosition(50, 50, 0);
        Skelly skelly = components.register(id, new Skelly(Skelly.Type.HUMAN));
        Arm spine = skelly.growArm(Arm.Type.SPINE, 2);
        
        id = spine.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_waist_0.png", TextureMeta.SOUTH));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 18));
        
        id = spine.push(entities.create());
        Transform trTorso = components.register(id, Transform.class, new Transform());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", TextureMeta.SOUTH));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 29));

        Arm rArm = skelly.growArm(Arm.Type.RARM, 2);
        id = rArm.push(entities.create());
        components.register(id, Transform.class, new Transform(-10, 0, 20)).parent = trTorso;
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_upper_arm_0.png", TextureMeta.NORTH_EAST));
        components.register(id, DirectionalVector.class, new DirectionalVector(-6, 0, -15));
        id = rArm.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_lower_arm_0.png", TextureMeta.NORTH));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, -10));
        
        
    }

    @Override
    public void render(float delta) {
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS]", Gdx.graphics.getFramesPerSecond()));
    	ScreenUtils.clear(0, 0, 0, 1);
        
        assets.batch().begin();
        
        entities.stream()
        .peek(id -> limbMovement.process(id, delta))
        .peek(id -> hierarchySystem.process(id, delta))
        .peek(id -> rotationSystem.process(id, delta))
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