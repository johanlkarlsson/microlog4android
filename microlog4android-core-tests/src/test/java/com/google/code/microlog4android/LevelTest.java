package com.google.code.microlog4android;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LevelTest {
	
	@Test
	public void testToInt() {
		assertEquals(Level.TRACE_INT, Level.TRACE.toInt());
	}
	
	@Test
	public void testToString() {
		assertEquals("TRACE", Level.TRACE.toString());
	}
}
