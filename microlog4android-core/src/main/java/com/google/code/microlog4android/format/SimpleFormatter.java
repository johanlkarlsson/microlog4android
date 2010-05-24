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
package com.google.code.microlog4android.format;

import com.google.code.microlog4android.Level;

/**
 * A simple formatter that only outputs the level, the message and the Throwable
 * object if available.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @since 0.1
 */
public final class SimpleFormatter implements Formatter {

	public static final String DEFAULT_DELIMITER = "-";

	private static final int INITIAL_BUFFER_SIZE = 256;

	StringBuffer buffer = new StringBuffer(INITIAL_BUFFER_SIZE);

	private String delimiter = DEFAULT_DELIMITER;

	/**
	 * Create a SimpleFormatter.
	 */
	public SimpleFormatter() {
	}

	/**
	 * Get the delimiter that is used between the different fields when logging.
	 * 
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Set the delimiter that is used between the different fields when logging.
	 * 
	 * @param delimiter
	 *            the delimiter to set
	 */
	public void setDelimeter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Format the given message and the Throwable object. The format is
	 * <code>{Level}{-message.toString()}{-t}</code>
	 * @param level
	 *            the logging level. If null, it is not appended to the String.
	 * @param message
	 *            the message. If null, it is not appended to the String.
	 * @param t
	 *            the exception. If null, it is not appended to the String.
	 * @return a String that is not null.
	 */
	public String format(String clientID, String name, long time, Level level, Object message, Throwable t) {
		if (buffer.length() > 0) {
			buffer.delete(0, buffer.length());
		}
		
		if(clientID != null){
			buffer.append(clientID);
			buffer.append(' ');
		}
		
		buffer.append(time);
		buffer.append(':');

		if (level != null) {
			buffer.append('[');
			buffer.append(level);
			buffer.append(']');
		}

		if (message != null) {
			buffer.append(delimiter);
			buffer.append(message);
		}

		if (t != null) {
			buffer.append(delimiter);
			buffer.append(t);
		}

		return buffer.toString();
	}

	public String[] getPropertyNames() {
		// We have no properties that can be set.
		return null;
	}

	public void setProperty(String name, String value) {
		// We have no properties that can be set.
	}
	
	
}
