/*
 * Copyright 2009 The Microlog project @sourceforge.net
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.slf4j.impl.repository;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.impl.MicrologLoggerAdapter;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.repository.CommonLoggerRepository;
import com.google.code.microlog4android.repository.LoggerNamesUtil;

/**
 * The <code>LoggerRepository</code> creates and contains all
 * <code>Logger</code> object(s).
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @author Jarle Hansen (hansjar@gmail.com)
 * @since 0.1
 * 
 */
public enum Slf4jLoggerRepository implements ILoggerFactory, CommonLoggerRepository {
	INSTANCE;

	private Slf4jRepositoryNode rootNode;
	private Hashtable<String, Slf4jRepositoryNode> leafNodeHashtable = new Hashtable<String, Slf4jRepositoryNode>(43);

	/**
	 * Create a <code>LoggerRepository</code>.
	 */
	private Slf4jLoggerRepository() {
		MicrologLoggerAdapter rootLogger = new MicrologLoggerAdapter("");
		rootLogger.getMicrologLogger().setLevel(Level.DEBUG);
		rootNode = new Slf4jRepositoryNode("", rootLogger);
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#getRootLogger()
	 */
	public Logger getRootLogger() {
		return rootNode.getLogger();
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#getLogger(java.lang.String)
	 */
	public synchronized Logger getLogger(String name) {
		Slf4jRepositoryNode node = leafNodeHashtable.get(name);
		MicrologLoggerAdapter logger = null;

		if (node == null) {
			logger = new MicrologLoggerAdapter(name);
			addLogger(logger);
		} else {
			logger = node.getLogger();
		}
		
		return logger;
	}

	/**
	 * Adds the specified <code>Logger</code> to the tree.
	 * 
	 * @param logger
	 *            the <code>Logger</code> to add.
	 */
	void addLogger(MicrologLoggerAdapter logger) {
		String loggerName = logger.getName();

		Slf4jRepositoryNode currentNode = rootNode;
		String[] pathComponents = LoggerNamesUtil.getLoggerNameComponents(loggerName);
		for (String pathComponent : pathComponents) {
			Slf4jRepositoryNode child = currentNode.getChildNode(pathComponent);

			if (child == null) {
				// No child => add the child
				currentNode = createNewChildNode(pathComponent, currentNode);
			}
		}

		// Add the leaf node
		if (pathComponents.length > 0) {
			String leafName = LoggerNamesUtil.getClassName(pathComponents);
			Slf4jRepositoryNode leafNode = new Slf4jRepositoryNode(leafName, logger, currentNode);
			currentNode.addChild(leafNode);
			leafNodeHashtable.put(loggerName, leafNode);
		}
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#setLevel(java.lang.String,
	 *      com.google.code.microlog4android.Level)
	 */
	public void setLevel(String name, Level level) {
		// Check if name the name is a leaf node
		Slf4jRepositoryNode leafNode = leafNodeHashtable.get(name);

		if (leafNode != null) {
			leafNode.getLogger().getMicrologLogger().setLevel(level);
		} else {
			Slf4jRepositoryNode currentNode = rootNode;
			String[] pathComponents = LoggerNamesUtil.getLoggerNameComponents(name);
			for (String pathComponent : pathComponents) {
				Slf4jRepositoryNode child = currentNode.getChildNode(pathComponent);

				if (child == null) {
					// No child => add the child
					currentNode = createNewChildNode(pathComponent, currentNode);
				}
			}

			if (currentNode != null) {
				currentNode.getLogger().getMicrologLogger().setLevel(level);
			}
		}
	}

	private Slf4jRepositoryNode createNewChildNode(final String pathComponent, final Slf4jRepositoryNode currentNode) {
		Slf4jRepositoryNode newChild = new Slf4jRepositoryNode(pathComponent, currentNode);
		currentNode.addChild(newChild);

		return newChild;
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#getEffectiveLevel(net.sf.microlog
	 *      .core.Logger)
	 */
	public Level getEffectiveLevel(String loggerName) {
		Level effectiveLevel = null;
		Slf4jRepositoryNode currentNode = leafNodeHashtable.get(loggerName);

		while (effectiveLevel == null && currentNode != null) {
			MicrologLoggerAdapter micrologLoggerAdapter = currentNode.getLogger();
			com.google.code.microlog4android.Logger logger = micrologLoggerAdapter.getMicrologLogger();
			effectiveLevel = logger.getLevel();
			currentNode = currentNode.getParent();
		}
		
		return effectiveLevel;
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#contains(java.lang.String)
	 */
	public boolean contains(String name) {
		return leafNodeHashtable.containsKey(name);
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#numberOfLeafNodes()
	 */
	public int numberOfLeafNodes() {
		return leafNodeHashtable.size();
	}

	/**
	 * Reset the tree.
	 */
	public void reset() {
		rootNode.resetLogger();
		leafNodeHashtable.clear();
	}

	/**
	 * Shutdown the <code>LoggerRepository</code>, i.e. release all the
	 * resources.
	 */
	public void shutdown() {
		Enumeration<Slf4jRepositoryNode> leafNodes = leafNodeHashtable.elements();

		while (leafNodes.hasMoreElements()) {
			Slf4jRepositoryNode node = leafNodes.nextElement();
			MicrologLoggerAdapter logger = node.getLogger();

			if (logger != null) {
				try {
					logger.getMicrologLogger().close();
				} catch (IOException e) {
				}
			}
		}
	}
}
