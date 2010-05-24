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

import java.util.Hashtable;

import org.slf4j.impl.MicrologLoggerAdapter;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.repository.AbstractRepositoryNode;

/**
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * 
 */
public class Slf4jRepositoryNode extends AbstractRepositoryNode {
	private Slf4jRepositoryNode parent;

	private Hashtable<String, Slf4jRepositoryNode> children = new Hashtable<String, Slf4jRepositoryNode>(17);

	private MicrologLoggerAdapter logger;

	/**
	 * Create a <code>TreeNode</code> with the specified name and the associated
	 * <code>Logger</code>.
	 * 
	 * @param name
	 *            the name of the <code>TreeNode</code>
	 * @param logger
	 *            the <code>Logger</code> to be associated with the
	 *            <code>TreeNode</code>
	 */
	public Slf4jRepositoryNode(String name, MicrologLoggerAdapter logger) {
		this.name = name;
		this.logger = logger;
	}

	public Slf4jRepositoryNode(String name, Slf4jRepositoryNode parent) {
		this.name = name;
		this.parent = parent;
		this.logger = new MicrologLoggerAdapter(new Logger(name));
	}

	public Slf4jRepositoryNode(String name, MicrologLoggerAdapter logger, Slf4jRepositoryNode parent) {
		this.name = name;
		this.logger = logger;
		this.parent = parent;
	}

	public void addChild(Slf4jRepositoryNode child) {
		children.put(child.getName(), child);
	}

	/**
	 * @return the logger
	 */
	public MicrologLoggerAdapter getLogger() {
		return logger;
	}

	public Slf4jRepositoryNode getChildNode(String name) {
		return children.get(name);
	}

	/**
	 * Remove all the children.
	 */
	public void resetLogger() {
		children.clear();
		logger.getMicrologLogger().resetLogger();
		logger.getMicrologLogger().setLevel(Level.DEBUG);
	}

	/**
	 * @return the parent
	 */
	public Slf4jRepositoryNode getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Slf4jRepositoryNode parent) {
		this.parent = parent;
	}

}
