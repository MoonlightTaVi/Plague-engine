package com.github.tavi.plague.ecs.spatial.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.tavi.plague.ecs.Components;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.util.io.Assets;

public class Renderer implements VisibleSystem {
	
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private Components components = Components.get();

	@Override
	public void process(int entityId, float delta) {
		Texture texture = null;
		TextureMeta meta = null;
		Transform transform = null;
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(meta = components.get(entityId, TextureMeta.class)) == null ||
				(texture = assets.texture(meta.path())) == null) {
			return;
		}
		
		float x = transform.x();
		float y = transform.y();
		float z = transform.z();
		float scaleX = 1;
		float scaleY = 1;
		float rotation = transform.rotationY;
		boolean flipX = false;
		boolean flipY = false;
		
		batch.draw(
				texture, 
				x - meta.originX(), 
				y + z - meta.originY(), 
				meta.originX(), 
				meta.originY(), 
				meta.width(), 
				meta.height(), 
				scaleX, 
				scaleY, 
				rotation, 
				meta.srcX(), 
				meta.srcY(), 
				meta.srcWidth(), 
				meta.srcHeight(), 
				flipX, 
				flipY
				);
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
