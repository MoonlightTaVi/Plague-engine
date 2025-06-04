package com.github.tavi.plague.ecs.spatial;

import com.github.tavi.plague.util.Vectors;

@Deprecated
public record Rotation(float[] axes) {
	
	public Rotation() {
		this(new float[] { 0, 0, 0 });
	}
	
	public Rotation(float xAxis, float yAxis, float zAxis) {
		this(new float[] { xAxis, yAxis, zAxis });
	}
	
	public void lookAt(float rotationZ) {
		axes[2] = Math.round(Vectors.keep360(rotationZ) / 45) * 45;
	}
	
	public float x() {
		return axes[0];
	}
	
	public float y() {
		return axes[1];
	}
	
	public float z() {
		return axes[2];
	}
	
	public float setX(float value) {
		axes[0] = value;
		axes[0] = Vectors.keep360(axes[0]);
		return axes[0];
	}
	
	public float setY(float value) {
		axes[1] = value;
		axes[1] = Vectors.keep360(axes[1]);
		return axes[1];
	}
	
	public float setZ(float value) {
		axes[2] = value;
		axes[2] = Vectors.keep360(axes[2]);
		return axes[2];
	}
	
	public float x(float delta) {
		axes[0] += delta;
		axes[0] = Vectors.keep360(axes[0]);
		return axes[0];
	}
	
	public float y(float delta) {
		axes[1] += delta;
		axes[1] = Vectors.keep360(axes[1]);
		return axes[1];
	}
	
	public float z(float delta) {
		axes[2] += delta;
		axes[2] = Vectors.keep360(axes[2]);
		return axes[2];
	}
	
}
