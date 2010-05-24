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

package com.google.code.microlog4android.repository;

import java.util.Hashtable;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;




/**
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * 
 */
public class MicrologRepositoryNode extends AbstractRepositoryNode {
	private MicrologRepositoryNode parent = null;

	private Hashtable<String, MicrologRepositoryNode> children = new Hashtable<String, MicrologRepositoryNode>(17);

	private Logger logger;

	/**
	 * Create a <code>TreeNode</code> with the specified name and the
	 * associated <code>Logger</code>.
	 * 
	 * @param name
	 *            the name of the <code>TreeNode</code>
	 * @param logger
	 *            the <code>Logger</code> to be associated with the
	 *            <code>TreeNode</code>
	 */
	public MicrologRepositoryNode(String name, Logger logger) {
		this.name = name;
		this.logger = logger;
	}
	
	public MicrologRepositoryNode(String name, MicrologRepositoryNode parent) {
		this.name = name;
		this.parent = parent;
		this.logger = new Logger(name);
		logger.setCommonRepository(DefaultLoggerRepository.INSTANCE);
	}
	
	public MicrologRepositoryNode(String name, Logger logger, MicrologRepositoryNode parent) {
		this.name = name;
		this.logger = logger;
		this.parent = parent;
	}


	public void addChild(MicrologRepositoryNode child) {
		children.put(child.getName(), child);
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	public MicrologRepositoryNode getChildNode(String name) {
		return children.get(name);
	}
	
	/**
	 * Remove all the children.
	 */
	public void resetLogger(){
		children.clear();
		logger.resetLogger();
		logger.setLevel(Level.DEBUG);
	}

	/**
	 * @return the parent
	 */
	public MicrologRepositoryNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(MicrologRepositoryNode parent) {
		this.parent = parent;
	}
}
