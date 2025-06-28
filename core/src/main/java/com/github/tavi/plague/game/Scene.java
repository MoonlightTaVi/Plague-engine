package com.github.tavi.plague.game;

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
import com.github.tavi.plague.ecs.util.IdChain;
import com.github.tavi.plague.ecs.util.ParentRef;
import com.github.tavi.plague.util.io.Assets;

/** The main implementation of LibGDX's Screen, used in Plague Engine. */
public class Scene implements Screen {
	
	// TEMPORARY --->
	private Components components = Components.get();
	private ECSystem lookDirectionSystem = new EcsLookRotation();
	private ECSystem renderer = new Renderer();
	private ECSystem hierarchySystem = new EcsHierarchy();
	private ECSystem rotationSystem = new EcsBoneRotation();
	private ECSystem limbMovement = new FabrikSystem();
	private ECSystem skeletonLooking = new EcsSkeletonLooking();
	private Assets assets = Assets.get();
	private Entities entities = Entities.get();
	
	private InputHandler input;
	
	private Vector2 cursor = new Vector2();
	// <--- TEMPORARY
	
    @Override
    public void show() {
    	IkMovementStrategy.setupStrategies();
    	
    	int entityId = entities.create();
    	int runningId = 0;
        Transform t = components.register(entityId, new Transform());
        components.register(entityId, MovementState.DANCING);
        t.worldPosition.set(50, 70, 0);
        
        LookVector lookingAt = components.register(entityId, new LookVector(0, -1, 0));
        input = new InputHandler(lookingAt);
        
        Skeleton skelly = components.register(entityId, new Skeleton(SkeletonType.HUMAN));
        IdChain spine = skelly.growArm(ArmType.SPINE, 3);
        
        int waistId = spine.push(entities.create());
        components.register(waistId, new ParentRef(entityId));
        components.register(waistId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_waist_0.png").centeredAt(TextureMeta.SOUTH));
        components.register(waistId, new BoneVector(0, 0, 18));
        
        int torsoId = spine.push(entities.create());
        components.register(torsoId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_torso_0.png").centeredAt(TextureMeta.SOUTH));
        components.register(torsoId, new BoneVector(0, 0, 29));
        
        runningId = spine.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_head_0.png").centeredAt(TextureMeta.SOUTH));
        components.register(runningId, new BoneVector(0, 0, 12));

        IdChain rArm = skelly.growArm(ArmType.RARM, 3);
        runningId = rArm.push(entities.create());
        components.register(runningId, new Transform(-10, 0, 20));
        components.register(runningId, new ParentRef(torsoId));
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_upper_arm_0.png").centeredAt(TextureMeta.NORTH_EAST));
        components.register(runningId, new BoneVector(-6, 0, -19));
        runningId = rArm.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_lower_arm_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -18));
        runningId = rArm.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_hand_0.png").centeredAt(TextureMeta.NORTH_EAST));
        components.register(runningId, new BoneVector(0, 0, -15));
        
        IdChain lArm = skelly.growArm(ArmType.LARM, 3);
        runningId = lArm.push(entities.create());
        components.register(runningId, new Transform(10, 0, 20));
        components.register(runningId, new ParentRef(torsoId));
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_upper_arm_0.png").centeredAt(TextureMeta.NORTH_WEST));
        components.register(runningId, new BoneVector(6, 0, -19));
        runningId = lArm.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_lower_arm_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -17));
        runningId = lArm.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_hand_0.png").centeredAt(TextureMeta.NORTH_WEST));
        components.register(runningId, new BoneVector(0, 0, -15));

        IdChain rLeg = skelly.growArm(ArmType.RLEG, 3);
        runningId = rLeg.push(entities.create());
        components.register(runningId, new Transform(-6, 0, -3));
        components.register(runningId, new ParentRef(waistId));
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_upper_leg_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -20));
        runningId = rLeg.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_lower_leg_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -30));
        runningId = rLeg.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta().fromImage("textures/real-male/male_foot_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(-5, 0, 0));

        IdChain lLeg = skelly.growArm(ArmType.LLEG, 3);
        runningId = lLeg.push(entities.create());
        components.register(runningId, new Transform(6, 0, -3));
        components.register(runningId, new ParentRef(waistId));
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_upper_leg_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -20));
        runningId = lLeg.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_lower_leg_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(0, 0, -30));
        runningId = lLeg.push(entities.create());
        components.register(runningId, TextureMeta.class, new SheetMeta(true).fromImage("textures/real-male/male_foot_0.png").centeredAt(TextureMeta.NORTH));
        components.register(runningId, new BoneVector(5, 0, 0));
        
        
    }

    @Override
    public void render(float delta) {
    	cursor.set(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY());
    	Gdx.graphics.setTitle(String.format("Plague Engine [%dFPS] %s", Gdx.graphics.getFramesPerSecond(), cursor));
    	ScreenUtils.clear(0, 0, 0, 1);
    	
    	input.handleInput(delta);
        
        assets.batch().begin();
        
        entities.stream()
        .peek(id -> lookDirectionSystem.process(id, delta))
        .peek(id -> limbMovement.process(id, delta))
        .peek(id -> skeletonLooking.process(id, delta))
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