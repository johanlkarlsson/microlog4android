/*
 * Copyright 2008 The Microlog project @sourceforge.net
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.microlog4android;

import java.util.Vector;

/**
 * A class that stores <code>Object</code>s in a cyclic buffer. The
 * <code>CyclicBuffer</code> is totally synchronized.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 */
public class CyclicBuffer {

	public static final int DEFAULT_BUFFER_SIZE = 10;

	private int bufferSize;
	private Object[] buffer;
	int currentIndex = -1;
	int currentOldestIndex = -1;

	private int length;

	/**
	 * Create a <code>CyclicBuffer</code> with the default buffer size.
	 */
	public CyclicBuffer() {
		bufferSize = DEFAULT_BUFFER_SIZE;
		buffer = new Object[bufferSize];
	}

	/**
	 * Create a <code>CyclicBuffer</code> with the specified buffer size.
	 * 
	 * @param bufferSize
	 *            the size of the buffer.
	 */
	public CyclicBuffer(int bufferSize) throws IllegalArgumentException {
		if (bufferSize < 0) {
			throw new IllegalArgumentException("Not allowed to resize to a negative size.");
		}

		this.bufferSize = bufferSize;
		buffer = new Object[bufferSize];
	}

	/**
	 * Get the buffer size.
	 * 
	 * @return the bufferSize the size of the buffer.
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * The current length of the buffer, i.e. how many <code>Object</code>s that
	 * are stored in the buffer.
	 * 
	 * @return the length of the buffer.
	 */
	public int length() {
		return length;
	}

	/**
	 * Resize the buffer. The old objects are discarded.
	 * 
	 * @param newSize
	 *            the new size of the buffer.
	 * @throws IllegalArgumentException
	 *             if the <code>newSize</code> is negative.
	 */
	public synchronized void resize(int newSize) throws IllegalArgumentException {
		if (newSize < 0) {
			throw new IllegalArgumentException("Not allowed to resize to a negative size.");
		}

		this.bufferSize = newSize;
		buffer = new Object[bufferSize];
	}

	/**
	 * 
	 * @param object
	 *            the <code>Object</code> to add.
	 * @throws IllegalArgumentException
	 *             if the object is null.
	 */
	public synchronized void add(Object object) throws IllegalArgumentException {
		if (object == null) {
			throw new IllegalArgumentException("You are not allowed to add an Object that is null.");
		}

		currentIndex = (currentIndex + 1) % buffer.length;
		buffer[currentIndex] = object;

		if (length < bufferSize) {
			length++;
			currentOldestIndex = 0;
		} else {
			currentOldestIndex = (currentOldestIndex + 1) % buffer.length;
		}
	}

	/**
	 * Get the oldest <code>Object</code> in the buffer. This is removed from
	 * the buffer.
	 * 
	 * @return the oldest <code>Object</code> in the buffer. If no more objects
	 *         are in the buffer, <code>null</code> is returned.
	 */
	public synchronized Object get() {
		Object object = null;

		if (length > 0) {
			object = buffer[currentOldestIndex];
			buffer[currentOldestIndex] = null;
			length--;
			if (length != 0) {
				currentOldestIndex = (currentOldestIndex + 1) % buffer.length;
			} else {
				currentOldestIndex = -1;
				currentIndex = -1;
			}
		}

		return object;
	}

	/**
	 * Clear the buffer. The buffer size is still the same.
	 */
	public synchronized void clear() {
		if (currentIndex > -1) {
			buffer = new Object[bufferSize];
			length = 0;
			currentIndex = -1;
			currentOldestIndex = -1;
		}
	}

	/**
	 * Get the internal buffer as a <code>Vector</code>. The content is copied.
	 * 
	 * @return a <code>Vector</code> with buffer content.
	 */
	public synchronized Vector getAsVector() {
		Vector vector = new Vector(length);

		for (int index = 0; index < length; index++) {
			vector.addElement(buffer[index]);
		}

		return vector;
	}
}
