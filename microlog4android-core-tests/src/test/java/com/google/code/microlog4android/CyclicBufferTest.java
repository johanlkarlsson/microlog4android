package com.google.code.microlog4android;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CyclicBufferTest {
	private CyclicBuffer cyclicBuffer;

	@Before
	public void setup() {
		cyclicBuffer = new CyclicBuffer();
	}
	
	@Test
	public void testResize() {
		cyclicBuffer.resize(CyclicBuffer.DEFAULT_BUFFER_SIZE + 1);

		assertEquals((CyclicBuffer.DEFAULT_BUFFER_SIZE + 1), cyclicBuffer.getBufferSize());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResizeNegativeSize() {
		cyclicBuffer.resize(-1);
	}

	@Test
	public void testAddOneObject() {
		cyclicBuffer.add("test");

		assertEquals(1, cyclicBuffer.length());
	}

	@Test
	public void testAddMultipleObjects() {
		cyclicBuffer.add("test");
		cyclicBuffer.add("test");
		cyclicBuffer.add("test");

		assertEquals(3, cyclicBuffer.length());
	}

	@Test
	public void testAddMoreObjectsThanBufferLength() {
		for (int i = 0; i < (CyclicBuffer.DEFAULT_BUFFER_SIZE + 5); i++) {
			cyclicBuffer.add("test");
		}

		assertEquals(5, cyclicBuffer.currentOldestIndex);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullObject() {
		cyclicBuffer.add(null);
	}

	@Test
	public void testGet() {
		for (int i = 0; i < CyclicBuffer.DEFAULT_BUFFER_SIZE; i++) {
			cyclicBuffer.add("test" + i);
		}

		assertEquals("test0", cyclicBuffer.get());
		assertEquals(CyclicBuffer.DEFAULT_BUFFER_SIZE - 1, cyclicBuffer.length());
		assertEquals(1, cyclicBuffer.currentOldestIndex);
	}

	@Test
	public void testClear() {
		for (int i = 0; i < CyclicBuffer.DEFAULT_BUFFER_SIZE; i++) {
			cyclicBuffer.add("test" + i);
		}

		cyclicBuffer.clear();

		assertEquals(0, cyclicBuffer.length());
		assertEquals(-1, cyclicBuffer.currentIndex);
		assertEquals(-1, cyclicBuffer.currentOldestIndex);
	}

	@Test
	public void testClearEmptyBuffer() {
		cyclicBuffer.clear();

		assertEquals(0, cyclicBuffer.length());
		assertEquals(-1, cyclicBuffer.currentIndex);
		assertEquals(-1, cyclicBuffer.currentOldestIndex);
	}
}
