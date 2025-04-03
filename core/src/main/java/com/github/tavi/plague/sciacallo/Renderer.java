package com.github.tavi.plague.sciacallo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.sciacallo.components.TextureMeta;
import com.github.tavi.plague.shared.Assets;
import com.github.tavi.plague.shared.Vectors;
import com.github.tavi.plague.shared.components.Transform;
import com.github.tavi.plague.shared.systems.VisibleProcessor;

public class Renderer implements VisibleProcessor {
	
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private Catti components = Catti.get();

	@Override
	public void process(int entityId) {
		Texture texture = null;
		TextureMeta meta = null;
		Transform transform = null;
		Vector3 vector = components.get(entityId, Vector3.class);
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(meta = components.get(entityId, TextureMeta.class)) == null ||
				(texture = assets.texture(meta.path())) == null) {
			return;
		}
		
		float scaleX = 1;
		float scaleY = 1;
		float rotation = vector != null ? Vectors.angle(Vectors.NORTH, new Vector2(vector.x, vector.y)) : 0;
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
