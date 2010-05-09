package com.google.code.microlog4android.integration.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IntegrationTests extends TestCase {
    
	public void testSomething() {
		Logger logger = LoggerFactory.getLogger(IntegrationTests.class);
		logger.debug("integration tests");
		
		Assert.assertEquals(2, 2);
	}
}