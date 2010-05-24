package org.slf4j.impl.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.code.microlog4android.Level;


public class Slf4jLoggerRepositoryTest {
	private Slf4jLoggerRepository slf4jLoggerRepository;
	
	@Before
	public void setup() {
		slf4jLoggerRepository = Slf4jLoggerRepository.INSTANCE;
	}
	
	@After
	public void teardown() {
		slf4jLoggerRepository.reset();
	}
	
	@Test
	public void testConstructorRootLoggerCreation() {
		assertTrue(slf4jLoggerRepository.getRootLogger().isDebugEnabled());
		assertEquals("", slf4jLoggerRepository.getRootLogger().getName());
	}
	
	@Test
	public void testGetLoggerNewInstance() {
		slf4jLoggerRepository.getLogger(Slf4jLoggerRepositoryTest.class.getName());
		
		assertTrue(slf4jLoggerRepository.contains(Slf4jLoggerRepositoryTest.class.getName()));
	}
	
	@Test
	public void testGetLoggerExistingInstance() {
		Logger logger1 = slf4jLoggerRepository.getLogger(Slf4jLoggerRepositoryTest.class.getName());
		Logger logger2 = slf4jLoggerRepository.getLogger(Slf4jLoggerRepositoryTest.class.getName());
		
		assertEquals(logger1.getName(), logger2.getName());
		assertEquals(logger1, logger2);
		assertEquals(1, slf4jLoggerRepository.numberOfLeafNodes());
	}
	
	@Test
	public void testSetLevelExistingLogger() {
		Logger logger = slf4jLoggerRepository.getLogger(Slf4jLoggerRepositoryTest.class.getName());
		slf4jLoggerRepository.setLevel(Slf4jLoggerRepositoryTest.class.getName(), Level.INFO);
		
		assertTrue(logger.isInfoEnabled());
	}
}
