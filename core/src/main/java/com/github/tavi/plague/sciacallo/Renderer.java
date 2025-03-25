package com.github.tavi.plague.sciacallo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.tavi.plague.sciacallo.components.TextureMeta;
import com.github.tavi.plague.shared.Assets;
import com.github.tavi.plague.shared.components.Transform;
import com.github.tavi.plague.shared.systems.EntityProcessor;
import com.github.tavi.plague.shared.systems.VisibleProcessor;

public class Renderer implements EntityProcessor<Integer>, VisibleProcessor {
	
	private Assets assets = Assets.get();
	private SpriteBatch batch = assets.batch();
	private Membra components = Membra.get();

	@Override
	public void process(Integer entityId) {
		Texture texture = null;
		TextureMeta meta = null;
		Transform transform = null;
		if ((transform = components.get(entityId, Transform.class)) == null ||
				(meta = components.get(entityId, TextureMeta.class)) == null ||
				(texture = assets.texture(meta.path())) == null) {
			return;
		}
		
		float scaleX = 1;
		float scaleY = 1;
		float rotation = 0;
		boolean flipX = false;
		boolean flipY = false;
		
		batch.draw(texture, transform.x - meta.originX(), transform.y - meta.originY(), meta.originX(), meta.originY(), meta.width(), meta.height(), scaleX, scaleY, rotation, meta.srcX(), meta.srcY(), meta.srcWidth(), meta.srcHeight(), flipX, flipY);
	}

}
