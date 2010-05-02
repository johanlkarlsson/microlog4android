package com.google.code.microlog4android;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StopWatchTest {
	private StopWatch stopWatch;
	
	@Before
	public void setup() {
		stopWatch = new StopWatch();
		stopWatch.start();
		
		try {
			Thread.sleep(100);
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	@After
	public void teardown() {
		stopWatch.reset();
	}
	
	@Test
	public void testStartAndGetCurrentTime() {
		long returnedTime = stopWatch.getCurrentTime();
		
		assertTrue(returnedTime >= 100);
	}
	
	@Test
	public void testStop() {
		long finishedTime = stopWatch.stop();
		
		assertTrue(finishedTime >= 100);
	}
}
