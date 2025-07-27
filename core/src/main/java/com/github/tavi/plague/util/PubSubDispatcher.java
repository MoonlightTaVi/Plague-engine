package com.github.tavi.plague.util;

public interface PubSubDispatcher<T> {

	public boolean subscribe(T subscriber);
	public boolean unsubscribe(T subscriber);
	
}
