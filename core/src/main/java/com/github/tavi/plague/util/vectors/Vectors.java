package com.github.tavi.plague.util.vectors;

import com.badlogic.gdx.math.Vector2;

public class Vectors {
	
	public static Vector2 NORTH = new Vector2(0, 1);

	public static float keep360(float angle) {
		return (((((float)Math.toDegrees(angle)) % 360) + 540) % 360) - 180;
	}

	public static float keep180(float angle) {
		return (float)(Math.atan2(Math.sin(angle * Math.PI/180), Math.cos(angle * Math.PI/180)) * 180/Math.PI);
	}
	
	@Deprecated
	public static float[] rotateAround(float[] targetVector, float[] pivotVector, float degrees) {
		if (targetVector.length < 2 || pivotVector.length < 2) {
			return new float[2];
		}
		
		double rad = Math.toRadians(degrees);
		double x = (targetVector[0] - pivotVector[0]) * Math.cos(rad) - (targetVector[1] - pivotVector[1]) * Math.sin(rad);
		double y = (targetVector[0] - pivotVector[0]) * Math.sin(rad) + (targetVector[1] - pivotVector[1]) * Math.cos(rad);
		
		return new float[] { pivotVector[0] + (float) x, pivotVector[1] + (float) y };
	}
	
	public static float angle(Vector2 a, Vector2 b) {
		return angle(a.x, a.y, b.x, b.y);
	}
	
	public static float angle(float x1, float y1, float x2, float y2) {
		float dot = x1 * x2 + y1 * y2;
		float det = x1 * y2 - x2 * y1;
		return (float)Math.toDegrees(Math.atan2(det, dot));
	}
	
}
