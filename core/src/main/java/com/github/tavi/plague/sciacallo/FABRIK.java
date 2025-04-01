package com.github.tavi.plague.sciacallo;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.github.tavi.plague.sciacallo.components.skeleton.Skelly;
import com.github.tavi.plague.shared.Components;
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
		
		mapIdsToVectors(skelly.spine.IDs);
		
		reach(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
	}
	
	private Vector3[] arm;
	//private float armLength;
	private float[] bonesLength;
	
	private boolean inMarginOfError(Vector3 target) {
		return arm[arm.length - 1].dst(target) < error;
	}
	
	private void reach(Vector3 target) {
		for (int i = 0; i < 1000 && !inMarginOfError(target); i++) {
			iterate(target);
		}
		
		for (int i = arm.length - 1; i > 0; i--) {
			arm[i].sub(arm[i - 1]);
			arm[i].nor().scl(bonesLength[i - 1]);
		}
	}
	
	private Vector3[] mapIdsToVectors(int[] IDs) {
		Vector3[] result = Arrays.stream(IDs).boxed().map(id -> components.get(id, Vector3.class)).toArray(Vector3[]::new);
		
		for (int i = 1; i < result.length; i++) {
			result[i].add(result[i - 1]);
		}
		
		bonesLength = new float[result.length - 1];
		//armLength = 0;
		for (int i = 0; i < result.length - 1; i++) {
			bonesLength[i] = result[i].dst(result[i + 1]);
			//armLength += bonesLength[i];
		}
		
		return result;
	}
	
	private void iterate(Vector3 target) {
		// Backward
		arm[arm.length - 1].set(target);
		for (int i = arm.length - 2; i >= 0; i--) {
			float length0 = bonesLength[i + 1];
			float length1 = arm[i + 1].dst(arm[i]);
			float lambda = length0 / length1;
			arm[i]
				.set(new Vector3(arm[i + 1]).scl(1 - lambda)
						.add(arm[i]).scl(lambda));
		}
		// Forward
		for (int i = 0; i < arm.length - 2; i++) {
			float length0 = bonesLength[i + 1];
			float length1 = arm[i + 1].dst(arm[i]);
			float lambda = length0 / length1;
			arm[i + 1]
				.set(new Vector3(arm[i]).scl(1 - lambda)
						.add(arm[i + 1]).scl(lambda));
		}
	}

}
