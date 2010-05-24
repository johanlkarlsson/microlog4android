package org.slf4j.impl.repository;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.impl.MicrologLoggerAdapter;

import com.google.code.microlog4android.Level;

public class Slf4jRepositoryNodeTest {
	
	@Test
	public void testResetLogger() {
		Slf4jRepositoryNode slf4jRepositoryNode = new Slf4jRepositoryNode(Slf4jLoggerRepositoryTest.class.getName(), new MicrologLoggerAdapter(Slf4jLoggerRepositoryTest.class.getName()));
		slf4jRepositoryNode.getLogger().getMicrologLogger().setLevel(Level.INFO);
		
		Slf4jRepositoryNode childNode1 = new Slf4jRepositoryNode("child1", new MicrologLoggerAdapter(Slf4jLoggerRepositoryTest.class.getName()));
		Slf4jRepositoryNode childNode2 = new Slf4jRepositoryNode("child2", new MicrologLoggerAdapter(Slf4jLoggerRepositoryTest.class.getName()));
		
		slf4jRepositoryNode.addChild(childNode1);
		slf4jRepositoryNode.addChild(childNode2);
		
		slf4jRepositoryNode.resetLogger();
		
		assertNull(slf4jRepositoryNode.getChildNode("child1"));
		assertTrue(slf4jRepositoryNode.getLogger().isDebugEnabled());
	}
}
