package com.github.tavi.plague.engine.bitmask;

public interface BitMask {
	public Class<?>[] getInvolvedComponents();
	public int value();
	default boolean test(int againstMask) {
		int value = value();
		return (value & againstMask) == value;
	}
}
