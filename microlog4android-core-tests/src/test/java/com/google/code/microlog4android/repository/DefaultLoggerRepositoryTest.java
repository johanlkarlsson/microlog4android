package com.google.code.microlog4android.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.repository.DefaultLoggerRepository;

public class DefaultLoggerRepositoryTest {
	private DefaultLoggerRepository defaultLoggerRepository;

	@Before
	public void setup() {
		defaultLoggerRepository = DefaultLoggerRepository.INSTANCE;
	}

	@After
	public void teardown() {
		defaultLoggerRepository.reset();
	}
	
	@Test
	public void testConstructorRootLoggerCreation() {
		assertEquals(Level.DEBUG, defaultLoggerRepository.getRootLogger().getEffectiveLevel());
		assertEquals("", defaultLoggerRepository.getRootLogger().getName());
	}
	
	@Test
	public void testGetLoggerNewInstance() {
		Logger logger = defaultLoggerRepository.getLogger(DefaultLoggerRepositoryTest.class.getName());

		assertEquals(logger.getName(), DefaultLoggerRepositoryTest.class.getName());
	}

	@Test
	public void testGetLoggerExistingInstance() {
		Logger logger1 = defaultLoggerRepository.getLogger(DefaultLoggerRepositoryTest.class.getName());
		Logger logger2 = defaultLoggerRepository.getLogger(DefaultLoggerRepositoryTest.class.getName());

		assertEquals(logger1.getName(), logger2.getName());
		assertEquals(logger1, logger2);
		assertEquals(1, defaultLoggerRepository.numberOfLeafNodes());
	}

	@Test
	public void testAddLogger() {
		defaultLoggerRepository.addLogger(new Logger(DefaultLoggerRepositoryTest.class.getName()));
		
		assertTrue(defaultLoggerRepository.contains(DefaultLoggerRepositoryTest.class.getName()));
	}
	
	@Test
	public void testSetLevelExistingLogger() {
		Logger logger = defaultLoggerRepository.getLogger(DefaultLoggerRepositoryTest.class.getName());
		defaultLoggerRepository.setLevel(DefaultLoggerRepositoryTest.class.getName(), Level.INFO);
		
		assertEquals(defaultLoggerRepository.getEffectiveLevel(logger.getName()), Level.INFO);
	}
}
