package com.github.tavi.plague.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.tavi.plague.controls.InputHandler;
import com.github.tavi.plague.ecs.renderable.*;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.engine.SystemOrchestra;
import com.github.tavi.plague.engine.shared.Entities;
import com.github.tavi.plague.engine.shared.Entity;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private SystemOrchestra engine = null;
	//private Comparator<Integer> renderSort = new IsometricComparator();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	
	//private InputHandler input;
	
	private Vector2 cursor = new Vector2();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	engine = new SystemOrchestra(new EcsRenderer());
    	
    	TextureMeta meta = new SheetMeta().fromImage("textures/male_0.png").withFrames(6).build();
    	Entity entity = new Entity(
    			entities.createId(),
    			meta,
    			new Transform(),
    			new Rotation()
    			);
    	engine.subscribe(entity);
    }

    @Override
    public void render(float delta) {
    	cursor.set(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY());
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS] %s", Gdx.graphics.getFramesPerSecond(), cursor));
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	//input.handleInput(delta);
        
        assets.batch().begin();
        
        engine.process(delta);
        
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