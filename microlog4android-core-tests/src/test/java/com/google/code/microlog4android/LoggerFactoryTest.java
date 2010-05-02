package com.google.code.microlog4android;

import org.junit.Test;

public class LoggerFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void testGetLoggerStringNullInput() {
		String s = null;
		LoggerFactory.getLogger(s);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetLoggerClassNullInput() {
		@SuppressWarnings("unchecked")
		Class c = null;
		LoggerFactory.getLogger(c);
	}
	
	
}
