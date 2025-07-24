package com.github.tavi.plague.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.controls.InputHandler;
import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.ecs.fabrik.*;
import com.github.tavi.plague.ecs.ik.*;
import com.github.tavi.plague.ecs.ik.strategies.IkMovementStrategy;
import com.github.tavi.plague.ecs.renderable.*;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.ecs.states.MovementState;
import com.github.tavi.plague.ecs.util.*;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private ECSystem rotateLook = new EcsLookRotation();
	private ECSystem renderer = new EcsRenderer();
	private ECSystem hierarchy = new EcsHierarchy();
	private ECSystem rotateBones = new EcsBoneRotation();
	private ECSystem ik = new FabrikSystem();
	private ECSystem rotateSkeleton = new EcsSkeletonLooking();
	private Comparator<Integer> renderSort = new IsometricComparator();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	
	private InputHandler input;
	
	private Vector2 cursor = new Vector2();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	
    }

    @Override
    public void render(float delta) {
    	cursor.set(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY());
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS] %s", Gdx.graphics.getFramesPerSecond(), cursor));
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//input.handleInput(delta);
        
        assets.batch().begin();
        
        entities.stream()
        .peek(id -> rotateLook.process(id, delta))
        .peek(id -> ik.process(id, delta))
        .peek(id -> rotateSkeleton.process(id, delta))
        .peek(id -> rotateBones.process(id, delta))
        .peek(id -> hierarchy.process(id, delta))
        .sorted(renderSort)
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