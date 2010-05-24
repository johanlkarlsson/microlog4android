package com.google.code.microlog4android.command.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.google.code.microlog4android.format.command.util.StringUtil;

public class StringUtilTest {
	
	@Test
	public void testExtractClassNameOnly() {
		String partialClassName = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 1);
		
		assertEquals("StringUtilTest", partialClassName);
	}
	
	@Test
	public void testExtractPartialClassName() {
		String partialClassName = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 3);
		
		assertEquals("command.util.StringUtilTest", partialClassName);
	}
	
	@Test
	public void testExtractEntireClassName() {
		String className = StringUtil.extractPartialClassName(StringUtilTest.class.getName(), 0);
		
		assertEquals(StringUtilTest.class.getName(), className);
	}
}
