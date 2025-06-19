package com.github.tavi.plague.util;

public interface NotNullValidator {
	
	default boolean checkNotNull(Object... objects) {
		boolean allPresent = true;
		for (Object object : objects) {
			allPresent = allPresent && object != null;
		}
		return allPresent;
	}
	
}
