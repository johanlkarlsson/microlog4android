package com.google.code.microlog4android;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.appender.LogCatAppender;
import com.google.code.microlog4android.repository.DefaultLoggerRepository;

public class LoggerTest {
	private Logger logger;

	@Before
	public void setup() {
		logger = new Logger(LoggerTest.class.getName());
		logger.setCommonRepository(DefaultLoggerRepository.INSTANCE);
	}

	@After
	public void teardown() {
		DefaultLoggerRepository.INSTANCE.reset();
	}

	@Test
	public void testGetEffectiveLevel() {
		DefaultLoggerRepository.INSTANCE.getLogger(LoggerTest.class.getName());
		DefaultLoggerRepository.INSTANCE.setLevel(LoggerTest.class.getName(), Level.DEBUG);

		Level returnLevel = logger.getEffectiveLevel();

		assertEquals(Level.DEBUG, returnLevel);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddAppenderNullInput() {
		logger.addAppender(null);
	}

	@Test
	public void testAddAppender() {
		logger.addAppender(new LogCatAppender());

		assertEquals(1, logger.getNumberOfAppenders());
	}

	@Test
	public void testAddAppenderDuplicateValue() {
		LogCatAppender lcAppender = new LogCatAppender();

		logger.addAppender(lcAppender);
		logger.addAppender(lcAppender);

		assertEquals(1, logger.getNumberOfAppenders());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveAppenderNullInput() {
		logger.removeAppender(null);
	}

	@Test
	public void testRemoveAppenderOpenLog() throws IOException {
		Appender mockedAppender = mock(Appender.class);
		when(mockedAppender.isLogOpen()).thenReturn(true);

		logger.addAppender(mockedAppender);
		logger.removeAppender(mockedAppender);

		verify(mockedAppender).close();
		assertEquals(0, logger.getNumberOfAppenders());
	}

	@Test
	public void testRemoveAppenderClosedLog() throws IOException {
		Appender mockedAppender = mock(Appender.class);
		when(mockedAppender.isLogOpen()).thenReturn(false);

		logger.addAppender(mockedAppender);
		logger.removeAppender(mockedAppender);

		verify(mockedAppender, times(0)).close();
		assertEquals(0, logger.getNumberOfAppenders());
	}

	@Test
	public void testRemoveAllAppenders() {
		LogCatAppender lcAppender1 = new LogCatAppender();
		LogCatAppender lcAppender2 = new LogCatAppender();

		logger.addAppender(lcAppender1);
		logger.addAppender(lcAppender2);

		logger.removeAllAppenders();

		assertEquals(0, logger.getNumberOfAppenders());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogNullLevelInput() {
		logger.log(null, "", null);
	}

	@Test
	public void testLogValidLevel() {
		Appender mockedAppender = mock(Appender.class);

		logger.addAppender(mockedAppender);

		DefaultLoggerRepository.INSTANCE.getLogger(LoggerTest.class.getName());
		DefaultLoggerRepository.INSTANCE.setLevel(LoggerTest.class.getName(), Level.DEBUG);

		logger.log(Level.INFO, "test");

		verify(mockedAppender).doLog(anyString(), anyString(), anyLong(), any(Level.class), anyString(),
				any(Throwable.class));
	}

	@Test
	public void testLogInvalidLevel() {
		Appender mockedAppender = mock(Appender.class);

		logger.addAppender(mockedAppender);

		DefaultLoggerRepository.INSTANCE.getLogger(LoggerTest.class.getName());
		DefaultLoggerRepository.INSTANCE.setLevel(LoggerTest.class.getName(), Level.INFO);

		logger.log(Level.DEBUG, "test");

		verifyZeroInteractions(mockedAppender);
	}
	
	@Test
	public void testDefaultLevel() {
		Logger defaultLevelLogger = DefaultLoggerRepository.INSTANCE.getLogger(LoggerTest.class.getName());
		
		assertEquals(Level.DEBUG, defaultLevelLogger.getEffectiveLevel());
	}
}
