package com.github.tavi.plague.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.controls.InputHandler;
import com.github.tavi.plague.ecs.renderable.*;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.system.ECSystem;
import com.github.tavi.plague.engine.entity.Entities;
import com.github.tavi.plague.engine.entity.util.EntityMask;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	//private ECSystem rotateLook = new EcsLookRotation();
	private ECSystem renderer = null;
	//private ECSystem hierarchy = new EcsHierarchy();
	//private ECSystem rotateBones = new EcsBoneRotation();
	//private ECSystem ik = new FabrikSystem();
	//private ECSystem rotateSkeleton = new EcsSkeletonLooking();
	private Comparator<Integer> renderSort = new IsometricComparator();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	
	private InputHandler input;
	
	private Vector2 cursor = new Vector2();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	EntityMask eMask = new EntityMask();
    	TextureMeta meta = new SheetMeta().fromImage("textures/male_0.png").withFrames(6).build();
    	Transform t = new Transform();
    	Rotation r = new Rotation();
    	components.addComponents(0, meta, t, r);
    	int mask = eMask.register(meta, t, r);
    	renderer = new EcsRenderer(mask);
    	renderer.subscribe(0);
    }

    @Override
    public void render(float delta) {
    	cursor.set(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY());
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS] %s", Gdx.graphics.getFramesPerSecond(), cursor));
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//input.handleInput(delta);
        
        assets.batch().begin();
        
        renderer.process(delta);
        
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