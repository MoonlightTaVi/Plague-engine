package com.github.tavi.plague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.ecs.behaviour.MovementState;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.ecs.spatial.renderable.*;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.*;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.components.*;
import com.github.tavi.plague.ecs.spatial.renderable.fabrik.strategies.LimbMovementStrategy;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private ECSystem renderer = new Renderer();
	private ECSystem hierarchySystem = new VisibleHierarchySystem();
	private ECSystem rotationSystem = new TextureRotationSystem();
	private ECSystem limbMovement = new FabrikSystem();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	
	private Vector2 cursor = new Vector2();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	LimbMovementStrategy.setupStrategies();
    	
    	int id = entities.create();
        Transform t = components.register(id, Transform.class, new Transform());
        components.register(id, MovementState.class, MovementState.DANCING);
        t.setWorldPosition(50, 50, 0);
        Skelly skelly = components.register(id, new Skelly(Skelly.Type.HUMAN));
        IdChain spine = skelly.growArm(ArmType.SPINE, 2);
        
        id = spine.push(entities.create());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_waist_0.png", TextureMeta.SOUTH));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 18));
        
        id = spine.push(entities.create());
        Transform trTorso = components.register(id, Transform.class, new Transform());
        components.register(id, TextureMeta.class, new TextureMeta("textures/real-male/male_torso_0.png", TextureMeta.SOUTH));
        components.register(id, DirectionalVector.class, new DirectionalVector(0, 0, 29));

        IdChain rArm = skelly.growArm(ArmType.RARM, 2);
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
    	cursor.set(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY());
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS] %s", Gdx.graphics.getFramesPerSecond(), cursor));
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