/**
 * Copyright 2011 Rowan Seymour
 *
 * This file is part of Kumva.
 *
 * Kumva is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kumva is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kumva. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ijuru.kumva.app.util;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Special collection class that discards oldest entries to ensure that size doesn't exceed the given
 * maximum
 */
public class SizeLimitedUniqueHistory<E> extends LinkedList<E> {

	private int maxSize;

	/**
	 * Creates a new size limited list
	 * @param maxSize the maximum size
	 */
	public SizeLimitedUniqueHistory(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Reduces the list size to the maximum
	 */
	protected void reduceToMaxSize() {
		while (size() > maxSize) {
			removeLast();
		}
	}

	/**
	 * @see java.util.LinkedList#add(Object)
	 */
	@Override
	public boolean add(E e) {
		if (contains(e)) {
			remove(e);
		}

		super.addFirst(e);

		reduceToMaxSize();
		return true;
	}

	/**
	 * @see java.util.LinkedList#add(int, Object)
	 */
	@Override
	public void add(int location, E object) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.LinkedList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> es) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.LinkedList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int location, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.LinkedList#addLast(Object)
	 */
	@Override
	public void addLast(E object) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.LinkedList#addFirst(Object)
	 */
	@Override
	public void addFirst(E object) {
		add(object);
	}
}