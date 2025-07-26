package com.github.tavi.plague.engine.system;

import java.util.*;

import com.github.tavi.plague.engine.PubSubDispatcher;

public class ECSystemDispatcher implements AbstractProcessor, PubSubDispatcher<Integer> {
	private EntityProcessor processor;
	private TreeSet<Integer> subscribers = new TreeSet<>();
	
	public ECSystemDispatcher(EntityProcessor processor) {
		this.processor = processor;
	}
	
	public boolean test(int mask) {
		return processor.test(mask);
	}
	
	@Override
	public boolean subscribe(Integer entityId) {
		return subscribers.add(entityId);
	}
	@Override
	public boolean unsubscribe(Integer entityId) {
		return subscribers.remove(entityId);
	}
	
	@Override
	public void process(float delta) {
		if (!processor.isActive) {
			return;
		}
		for (int sub : subscribers) {
			processor.process(sub, delta);
		}
	}
	
}
