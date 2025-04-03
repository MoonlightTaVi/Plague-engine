package com.github.tavi.plague.sciacallo;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.sciacallo.components.skeleton.Skelly;
import com.github.tavi.plague.shared.Components;
import com.github.tavi.plague.shared.components.Transform;
import com.github.tavi.plague.shared.systems.VisibleProcessor;

public class FABRIK implements VisibleProcessor {
	
	private float error = 0.1f;
	
	private Components components = Components.get();

	@Override
	public void process(int entityId) {
		Skelly skelly;
		if ((skelly = components.get(entityId, Skelly.class)) == null) {
			return;
		}
		
		// ======== TEMP
		
		Transform t = components.get(entityId, Transform.class);
		if (t != null) {
			offset.set(t.x, t.y, t.z);
		}
		
		arm = mapIdsToVectors(skelly.spine.IDs);
		
		reach(new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - 1 - Gdx.input.getY(), 0));
		
		for (int i = arm.length - 1; i > 0; i--) {
			//arm[i].sub(arm[i - 1]);
			arm[i].set(new Vector3(arm[i]).sub(arm[i - 1]).nor().scl(bonesLength[i]).add(arm[i - 1]));
		}
		
		for (int i = 0; i < skelly.spine.IDs.length; i++) {
			components.getOr(skelly.spine.IDs[i], Transform.class, () -> new Transform()).set(arm[i]);
		}
		
		restore();
		
	}
	
	private Vector3[] arm;
	private float armLength;
	private float[] bonesLength;
	private Vector3 offset = new Vector3();
	
	private boolean inMarginOfError(Vector3 target) {
		return arm[arm.length - 1].dst(target) < error;
	}
	
	private void reach(Vector3 target) {
		if (arm[0].dst(target) > armLength) {
			return;
		}
		
		for (int i = 0; i < 1000 && !inMarginOfError(target); i++) {
			iterate(target);
		}
	}
	
	private void restore() {
		for (int i = arm.length - 1; i > 0; i--) {
			arm[i].sub(arm[i - 1]);
			//arm[i].nor().scl(bonesLength[i]);
		}
		//arm[0].sub(offset);
		//arm[0].nor().scl(bonesLength[0]);
	}
	
	private Vector3[] mapIdsToVectors(int[] IDs) {
		//Vector3[] result = Arrays.stream(IDs).boxed().map(id -> components.get(id, Vector3.class)).toArray(Vector3[]::new);
		Vector3[] result = new Vector3[IDs.length + 1];
		result[0] = offset;
		for (int i = 1; i <= IDs.length; i++) {
			result[i] = components.get(IDs[i - 1], Vector3.class);
		}
		
		//result[0].add(offset);
		for (int i = 1; i < result.length; i++) {
			result[i].add(result[i - 1]);
		}
		
		bonesLength = new float[result.length];
		bonesLength[0] = 0;
		armLength = 0;
		for (int i = 1; i < result.length; i++) {
			bonesLength[i] = result[i].dst(result[i - 1]);
			//System.out.println(i + " " + bonesLength[i]);
			armLength += bonesLength[i];
		}
		System.out.println(armLength);
		
		return result;
	}
	
	private void iterate(Vector3 target) {
		// Backward
		arm[arm.length - 1].set(target);
		for (int i = arm.length - 2; i > 0; i--) {
			float length0 = bonesLength[i+1];
			float length1 = arm[i + 1].dst(arm[i]);
			float lambda = length0 / length1;
			arm[i]
				.set(new Vector3(arm[i + 1]).scl(1 - lambda)
						.add(new Vector3(arm[i]).scl(lambda)));
		}
		// Forward
		for (int i = 1; i < arm.length - 1; i++) {
			float length0 = bonesLength[i+1];
			float length1 = arm[i + 1].dst(arm[i]);
			float lambda = length0 / length1;
			//System.out.println(1 + " " + arm[i + 1]);
			arm[i + 1]
				.set(new Vector3(arm[i]).scl(1 - lambda)
						.add(new Vector3(arm[i + 1]).scl(lambda)));
			//System.out.println(2 + " " + arm[i + 1]);
		}
		//arm[0].set(offset);
	}

	@Override
	public boolean isVisible(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
