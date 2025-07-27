package com.github.tavi.plague.ecs.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.spatial.*;
import com.github.tavi.plague.engine.system.BaseEntityProcessor;
import com.github.tavi.plague.util.io.Assets;

public class EcsRenderer extends BaseEntityProcessor {
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();

	@Override
	public void process(int entityId, float delta) {
		TextureMeta meta = super.getComponent(entityId, TextureMeta.class);
		Transform transform = super.getComponent(entityId, Transform.class);
		Rotation rotation = super.getComponent(entityId, Rotation.class);
		render(meta, transform, rotation);
	}
	
	public void render(TextureMeta meta, Transform transform, Rotation rotation) {
		Texture texture = assets.texture(meta.path());
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

}
