package com.github.tavi.plague.engine.system;

import java.util.*;

import com.github.tavi.plague.engine.PubSubDispatcher;
import com.github.tavi.plague.engine.entity.util.BitMask;

public class ECSystemDispatcher implements AbstractProcessor, PubSubDispatcher<Integer>, BitMask {
	private BaseEntityProcessor processor;
	private TreeSet<Integer> subscribers = new TreeSet<>();
	
	public ECSystemDispatcher(BaseEntityProcessor processor) {
		this.processor = processor;
	}
	
	@Override
	public Class<?>[] getInvolvedComponents() {
		return processor.getInvolvedComponents();
	}
	@Override
	public int value() {
		return processor.value();
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
		if (!processor.isActive()) {
			return;
		}
		for (int sub : subscribers) {
			processor.process(sub, delta);
		}
	}
	
}
