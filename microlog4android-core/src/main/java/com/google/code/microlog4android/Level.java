/*
 * Copyright 2008 The Microlog project @sourceforge.net
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
package com.google.code.microlog4android;

/**
 * This class represent the logging level.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @since 0.1
 */
public final class Level {

	public static final int FATAL_INT = 16;

	public static final int ERROR_INT = 8;

	public static final int WARN_INT = 4;

	public static final int INFO_INT = 2;

	public static final int DEBUG_INT = 1;

	public static final int TRACE_INT = 0;
	
	public static final int OFF_INT = -1;
	
	/**
	 * The {@link String} representing the FATAL level
	 */
	public static final String FATAL_STRING = "FATAL";
	
	/**
	 * The {@link String} representing the ERROR level
	 */
	public static final String ERROR_STRING = "ERROR";
	
	/**
	 * The {@link String} representing the WARN level.
	 */
	public static final String WARN_STRING = "WARN";
	
	/**
	 * The {@link String} representing the INFO level.
	 */
	public static final String INFO_STRING = "INFO";
	
	/**
	 * The {@link String} representing the DEBUG level
	 */
	public static final String DEBUG_STRING = "DEBUG";
	
	/**
	 * The {@link String} representing the TRACE level.
	 */
	public static final String TRACE_STRING = "TRACE";
	
	/**
	 * The {@link String} representing the OFF level.
	 * @since 3.0
	 */
	public static final String OFF_STRING = "OFF";

	public static final Level FATAL = new Level(FATAL_INT, FATAL_STRING);

	public static final Level ERROR = new Level(ERROR_INT, ERROR_STRING);

	public static final Level WARN = new Level(WARN_INT, WARN_STRING);

	public static final Level INFO = new Level(INFO_INT, INFO_STRING);

	public static final Level DEBUG = new Level(DEBUG_INT, DEBUG_STRING);

	public static final Level TRACE = new Level(TRACE_INT, TRACE_STRING);
	
	public static final Level OFF = new Level(OFF_INT, OFF_STRING);

	int levelValue;

	private String levelString = "";

	/**
	 * Create a <code>Level</code> object.
	 * 
	 * @param level
	 *            the level to create. This should be set using one of the
	 *            constants defined in the class.
	 * @param levelString
	 *            the <code>String</code> that shall represent the level. This
	 *            should be set using one of the defined constants defined in
	 *            the class.
	 */
	private Level(int level, String levelString) {
		this.levelValue = level;
		this.levelString = levelString;
	}

	/**
	 * Return the integer level for this <code>Level</code>.
	 * 
	 * @return the integer level.
	 */
	public int toInt() {
		return levelValue;
	}

	/**
	 * Return a <code>String</code> representation for this <code>Level</code>.
	 * 
	 * @return a <code>String</code> representation for the <code>Level</code>.
	 */
	public String toString() {
		return levelString;
	}	
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {	
		boolean equals = false;
		
		if (obj instanceof Level) {
			Level compareLevel = (Level) obj;
			
			if(levelValue == compareLevel.levelValue){
				equals = true;
			}
		}
	
		return equals;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + levelValue;
		return hash;
	}
	
	
}
