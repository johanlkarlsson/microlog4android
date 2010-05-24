package com.google.code.microlog4android.integration.tests.slf4j;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.MicrologLoggerAdapter;
import org.slf4j.impl.repository.Slf4jLoggerRepository;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.appender.LogCatAppender;
import com.google.code.microlog4android.integration.tests.appender.JunitTestAppender;

public class Slf4jIntegrationTest extends TestCase {
	private JunitTestAppender testAppender;

	public void setUp() {
		testAppender = new JunitTestAppender();
	}
	
	public void tearDown() {
		Slf4jLoggerRepository.INSTANCE.reset();
	}

	private void setTestAppenders(final Logger logger) {
		((MicrologLoggerAdapter) logger).getMicrologLogger().addAppender(testAppender);
		((MicrologLoggerAdapter) logger).getMicrologLogger().addAppender(new LogCatAppender());
	}

	public void testGetLogger() {
		Logger stdLogger = LoggerFactory.getLogger(Slf4jIntegrationTest.class);
		setTestAppenders(stdLogger);

		stdLogger.debug("slf4j integration test logging");

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
		((MicrologLoggerAdapter)logger).getMicrologLogger().setLevel(Level.WARN);
		
		assertFalse(logger.isDebugEnabled());
		assertFalse(logger.isInfoEnabled());
		assertTrue(logger.isWarnEnabled());
	}
}