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
package com.google.code.microlog4android.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import android.util.Log;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;


/**
 * The <code>LoggerRepository</code> creates and contains all
 * <code>Logger</code> object(s).
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @author Jarle Hansen (hansjar@gmail.com)
 * @since 2.0
 * 
 */
public enum DefaultLoggerRepository implements LoggerRepository {
	INSTANCE;
	private static final String TAG = "Microlog.DefaultLoggerRepository";
	
	private RepositoryNode rootNode;
	
	private Hashtable<String, RepositoryNode> leafNodeHashtable = new Hashtable<String, RepositoryNode>(43);

	/**
	 * Create a <code>LoggerRepository</code>.
	 */
	private DefaultLoggerRepository() {
		Logger rootLogger = new Logger("");
		rootLogger.setLevel(Level.DEBUG);
		rootNode = new RepositoryNode("", rootLogger);
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#getRootLogger()
	 */
	public Logger getRootLogger() {
		return rootNode.logger;
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#getLogger(java.lang.String)
	 */
	public synchronized Logger getLogger(String name) {
		RepositoryNode node = (RepositoryNode) leafNodeHashtable.get(name);
		Logger logger = null;

		if (node == null) {
			logger = new Logger(name);
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
	synchronized void addLogger(Logger logger) {
		String loggerName = logger.getName();
		int beginIndex = 0;
		int endIndex = loggerName.indexOf('.');
		RepositoryNode currentNode = rootNode;

		while (endIndex != -1) {
			String pathComponent = loggerName.substring(beginIndex, endIndex);
			beginIndex = endIndex + 1;
			
			RepositoryNode child = currentNode.getChildNode(pathComponent);

			if (child != null) {
				// Child found => traverse down the tree.
				currentNode = child;
			} else {
				// No child => add the child
				RepositoryNode newChild = new RepositoryNode(pathComponent);
				newChild.setParent(currentNode);
				currentNode.addChild(newChild);
				currentNode = newChild;
			}

			endIndex = loggerName.indexOf('.', beginIndex);
		}

		// Add the leaf node
		String leafName = loggerName.substring(beginIndex, loggerName.length());
		RepositoryNode leafNode = new RepositoryNode(leafName, logger);
		leafNode.setParent(currentNode);
		currentNode.addChild(leafNode);
		leafNodeHashtable.put(loggerName, leafNode);
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#setLevel(java.lang.String,
	 *      com.google.code.microlog4android.Level)
	 */
	public void setLevel(String name, Level level) {

		// Check if name the name is a leaf node
		RepositoryNode leafNode = (RepositoryNode) leafNodeHashtable.get(name);

		if (leafNode != null) {
			leafNode.logger.setLevel(level);
		} else {

			int beginIndex = 0;
			int endIndex = name.indexOf('.');
			int nameLength = name.length();
			RepositoryNode currentNode = rootNode;

			while (beginIndex < nameLength && currentNode != null) {

				if (endIndex == -1) {
					endIndex = nameLength;
				}

				String pathComponent = name.substring(beginIndex, endIndex);
				beginIndex = endIndex + 1;

				RepositoryNode child = currentNode.getChildNode(pathComponent);

				if (child != null) {
					// Child found => traverse down the tree.
					currentNode = child;
				} else {
					// No child => add the child
					RepositoryNode newChild = new RepositoryNode(pathComponent);
					newChild.setParent(currentNode);
					currentNode.addChild(newChild);
					currentNode = newChild;
				}

				endIndex = name.indexOf('.', beginIndex);
			}

			if (currentNode != null) {
				currentNode.level = level;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.microlog.core.LoggerRepository#getEffectiveLevel(net.sf.microlog
	 * .core.Logger)
	 */
	public Level getEffectiveLevel(Logger logger) {

		Level effectiveLevel = null;
		RepositoryNode currentNode = (RepositoryNode) leafNodeHashtable
				.get(logger.getName());

		while (effectiveLevel == null && currentNode != null) {
			effectiveLevel = currentNode.getLevel();
			currentNode = currentNode.parent;
		}

		return effectiveLevel;
	}

	/**
	 * @see com.google.code.microlog4android.config.LoggerRepository#contains(java.lang.String)
	 */
	public boolean contains(String name) {
		return leafNodeHashtable.get(name) == null ? false : true;
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
		rootNode.removeAllChildren();
		Logger rootLogger = rootNode.logger;
		rootLogger.resetLogger();
		rootLogger.setLevel(Level.DEBUG);
		leafNodeHashtable.clear();
	}
	
	
	/**
	 * Shutdown the <code>LoggerRepository</code>, i.e. release all the
	 * resources.
	 */
	public void shutdown(){
		Enumeration<RepositoryNode> leafNodes = leafNodeHashtable.elements();
		
		while (leafNodes.hasMoreElements()) {
			RepositoryNode node = (RepositoryNode) leafNodes.nextElement();
			Logger logger = node.getLogger();
			
			if(logger != null){
				try {
					logger.close();
				} catch (IOException e) {
					Log.e(TAG, "Failed to close logger "+logger.getName());
				}
			}
		}
	}
	
	
}
