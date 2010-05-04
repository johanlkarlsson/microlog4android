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

import java.util.Hashtable;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;



/**
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * 
 */
class RepositoryNode {

	protected String name;
	
	protected RepositoryNode parent;

	protected Hashtable<String, RepositoryNode> children = new Hashtable<String, RepositoryNode>(17);

	protected Logger logger;
	
	protected Level level;

	/**
	 * Create a <code>TreeNode</code> with the specified name.
	 * 
	 * @param name
	 *            the name of the <code>TreeNode</code>
	 */
	public RepositoryNode(String name) {
		this.name = name;
	}

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
	public RepositoryNode(String name, Logger logger) {
		super();
		this.name = name;
		this.logger = logger;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void addChild(RepositoryNode child) {
		children.put(child.getName(), child);
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public Level getLevel(){
		Level nodeLevel = level;
		
		if(logger != null){
			nodeLevel = logger.getLevel();
		}
		
		return nodeLevel;
	}

	public RepositoryNode getChildNode(String name) {
		return (RepositoryNode) children.get(name);
	}
	
	/**
	 * Remove all the children.
	 */
	public void removeAllChildren(){
		children.clear();
	}

	/**
	 * @return the parent
	 */
	public RepositoryNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(RepositoryNode parent) {
		this.parent = parent;
	}
	
	
}
