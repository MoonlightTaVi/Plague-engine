package com.github.tavi.plague.ecs.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.*;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.util.io.Assets;

public class Renderer implements ECSystem {
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private Components components = Components.get();
	
	private Texture texture = null;
	private TextureMeta meta = null;
	private Transform transform = null;
	private Rotation rotation = null;

	@Override
	public void process(float delta) {
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

	@Override
	public boolean validate(int entityId) {
		transform = components.get(entityId, Transform.class);
		rotation = components.get(entityId, Rotation.class);
		meta = components.get(entityId, TextureMeta.class);
		return ECSystem.super.areNotNull(transform, meta, rotation);
	}

}
