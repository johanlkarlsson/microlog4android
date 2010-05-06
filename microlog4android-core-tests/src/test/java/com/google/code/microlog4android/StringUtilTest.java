package com.google.code.microlog4android;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StringUtilTest {
	
	@Test
	public void testExtractClassNameOnly() {
		String partialClassName = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 1);
		
		assertEquals("StringUtilTest", partialClassName);
	}
	
	@Test
	public void testExtractPartialClassName() {
		String partialClassName = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 3);
		
		assertEquals("code.microlog4android.StringUtilTest", partialClassName);
	}
	
	@Test
	public void testExtractEntireClassName() {
		String className = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 0);
		
		assertEquals(StringUtilTest.class.getName(), className);
	}
}
