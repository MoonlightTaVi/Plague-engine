package com.github.tavi.plague.engine;

public interface PubSubDispatcher<T> {

	public boolean subscribe(T subscriber);
	public boolean unsubscribe(T subscriber);
	
}
