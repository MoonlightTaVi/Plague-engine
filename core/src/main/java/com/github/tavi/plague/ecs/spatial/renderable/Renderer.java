package com.github.tavi.plague.ecs.spatial.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.ecs.CattiComponents;
import com.github.tavi.plague.ecs.spatial.Transform;
import com.github.tavi.plague.ecs.spatial.renderable.ik.BoneVector;
import com.github.tavi.plague.util.Vectors;
import com.github.tavi.plague.util.io.Assets;

public class Renderer implements VisibleSystem {
	
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private CattiComponents components = CattiComponents.get();

	@Override
	public void process(int entityId) {
		Texture texture = null;
		TextureMeta meta = null;
		Transform transform = null;
		BoneVector vector = components.get(entityId, BoneVector.class);
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(meta = components.get(entityId, TextureMeta.class)) == null ||
				(texture = assets.texture(meta.path())) == null) {
			return;
		}
		
		float scaleX = 1;
		float scaleY = 1;
		float rotation = vector != null ? Vectors.angle(new Vector2(vector.original().x, vector.original().y), new Vector2(vector.current().x, vector.current().y)) : 0;
		boolean flipX = false;
		boolean flipY = false;
		
		batch.draw(texture, transform.x - meta.originX(), transform.y - meta.originY(), meta.originX(), meta.originY(), meta.width(), meta.height(), scaleX, scaleY, rotation, meta.srcX(), meta.srcY(), meta.srcWidth(), meta.srcHeight(), flipX, flipY);
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
