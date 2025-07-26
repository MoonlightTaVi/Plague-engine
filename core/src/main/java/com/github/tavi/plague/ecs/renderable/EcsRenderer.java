package com.github.tavi.plague.ecs.renderable;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.engine.component.Components;
import com.github.tavi.plague.engine.system.ECSystem;
import com.github.tavi.plague.util.io.Assets;

public class EcsRenderer implements ECSystem {
	private int systemMask;
	
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private Components components = Components.get();
	private List<Integer> subscribers = new ArrayList<>();
	
	private Texture texture = null;
	private SheetMeta meta = null;
	private Transform transform = null;
	private Rotation rotation = null;

	public EcsRenderer(int systemMask) {
		this.systemMask = systemMask;
	}

	@Override
	public int getSystemMask() {
		return systemMask;
	}

	@Override
	public void subscribe(int entityId) {
		subscribers.add(entityId);
	}
	
	@Override
	public void process(float delta) {
		for (int id : subscribers) {
			prepare(id);
			render();
		}
	}
	
	private void render() {
		texture = assets.texture(meta.path());
		Vector3 worldPosition = transform.worldPosition;
		float x = worldPosition.x;
		float y = worldPosition.y;
		float z = worldPosition.z;
		float scaleX = 1;
		float scaleY = 1;
		float rotationY = rotation.rotationY;
		boolean flipX = meta.flipX();
		boolean flipY = meta.flipY();
		
		batch.draw(
				texture, 
				x - meta.originX(), 
				y + z - meta.originY(), 
				meta.originX(), 
				meta.originY(), 
				meta.size().x, 
				meta.size().y, 
				scaleX, 
				scaleY, 
				rotationY, 
				meta.srcX(), 
				meta.srcY(), 
				meta.srcWidth(), 
				meta.srcHeight(), 
				flipX, 
				flipY
				);
	}

	private void prepare(int entityId) {
		int count = 0;
		for (Object component : components.getComponents(entityId)) {
			if (component instanceof Transform t) {
				transform = t;
				count++;
			} else if (component instanceof Rotation r) {
				rotation = r;
				count++;
			} else if (component instanceof SheetMeta m) {
				meta = m;
				count++;
			}
			if (count >= 3) {
				break;
			}
		}
	}

}
