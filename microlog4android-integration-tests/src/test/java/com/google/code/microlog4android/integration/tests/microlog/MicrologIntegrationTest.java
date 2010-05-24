package com.google.code.microlog4android.integration.tests.microlog;

import junit.framework.TestCase;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.google.code.microlog4android.appender.LogCatAppender;
import com.google.code.microlog4android.integration.tests.appender.JunitTestAppender;
import com.google.code.microlog4android.integration.tests.slf4j.Slf4jIntegrationTest;
import com.google.code.microlog4android.repository.DefaultLoggerRepository;

public class MicrologIntegrationTest extends TestCase {
	private JunitTestAppender testAppender;

	public void setUp() {
		testAppender = new JunitTestAppender();
	}
	
	public void tearDown() {
		DefaultLoggerRepository.INSTANCE.reset();
	}

	private void setTestAppenders(final Logger logger) {
		logger.addAppender(testAppender);
		logger.addAppender(new LogCatAppender());
	}

	public void testGetLogger() {
		Logger stdLogger = LoggerFactory.getLogger(Slf4jIntegrationTest.class);
		setTestAppenders(stdLogger);

		stdLogger.debug("microlog integration test logging");

		assertEquals(1, testAppender.getLoggerList().size());
	}
	
	public void testDefaultLogLevel() {
		Logger logger = LoggerFactory.getLogger(Slf4jIntegrationTest.class);
		
		assertTrue(logger.isDebugEnabled());
		assertTrue(logger.isInfoEnabled());
		assertFalse(logger.isTraceEnabled());
	}
	
	public void testWarnLogLevel() {
		Logger logger = LoggerFactory.getLogger(Slf4jIntegrationTest.class);
		logger.setLevel(Level.WARN);
		
		assertFalse(logger.isDebugEnabled());
		assertFalse(logger.isInfoEnabled());
	}
}
