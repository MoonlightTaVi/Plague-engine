package com.github.tavi.plague.ecs.util;

import java.util.Iterator;

/**
 * A chain of Entity IDs with a fixed length.
 * Use it whenever you need to chain Entities.
 * <br>
 * Implements Iterable, making it possible to iterate over Entities.
 */
public class IdChain implements Iterable<Integer> {
	/** Current number of rings in the chain. */
	public int size = 0;
	/** An array of IDs, representing the chain. */
	public int[] IDs;
	
	/**
	 * Constructs the chain with a fixed length.
	 * @param length Maximum number of rings in the chain.
	 */
	public IdChain(int length) {
		IDs = new int[length];
	}
	
	/**
	 * Adds an Entity ID to the last vacant position in the chain.
	 * @param id Entity ID.
	 * @return The passed Entity ID for later use.
	 * @throws IndexOutOfBoundsException If no more vacant positions available.
	 */
	public int push(int id) throws IndexOutOfBoundsException {
		IDs[size] = id;
		size++;
		return id;
	}
	
	/**
	 * Maps the ID inside this chain to the corresponding Entity.
	 * @param idInChain Position of the ID inside the chain (NOT the ID of the Entity).
	 * @return Entity with this ID.
	 * @throws IndexOutOfBoundsException If idInChain is more than the size of the chain.
	 */
	public Entity getEntity(int idInChain) throws IndexOutOfBoundsException {
		return new Entity(IDs[idInChain]);
	}
	
	/**
	 * Maps IDs in the chain to Entities and returns the resulting array.
	 * @return Array of Entities.
	 * @see com.github.tavi.plague.ecs.util.Entity
	 */
	public Entity[] entities() {
		Entity[] entities = new Entity[IDs.length];
		int i = 0;
		for (int id : IDs) {
			entities[i++] = new Entity(id);
		}
		return entities;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IdIterator(this);
	}
	
	/**
	 * Used to iterate over entities in the chain.
	 */
	private class IdIterator implements Iterator<Integer> {
		private final int[] ids;
		private int currentId = 0;
		public IdIterator(IdChain parentIterable) {
			ids = parentIterable.IDs;
		}
		@Override
		public boolean hasNext() {
			return currentId < ids.length;
		}
		@Override
		public Integer next() {
			return ids[currentId++];
		}
	}
	
}
