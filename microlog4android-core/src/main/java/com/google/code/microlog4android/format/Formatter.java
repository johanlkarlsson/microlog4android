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
 * This the interface for all formatters. A <code>Formatter</code> is
 * responsible for formatting the log content.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @since 0.1
 */
public interface Formatter {

	/**
	 * Format the given message and the Throwable object.
	 * @param clientID
	 *            the id of the client
	 * @param name
	 *            the name of the logger.
	 * @param time
	 *            the time since the first logging has done (in milliseconds).
	 * @param level
	 *            the logging level
	 * @param message
	 *            the message
	 * @param t
	 *            the exception.
	 * 
	 * @return a String that is not null.
	 */
	String format(String clientID, String name, long time, Level level,
			Object message, Throwable t);
	
	/**
	 * Get the appender specific property names. This is workaround for the lack
	 * of reflection in Java ME and is used for configuration.
	 * 
	 * @return an array of the supported properties.
	 */
	String[] getPropertyNames();

	/**
	 * Set the specified property to the supplied value.
	 * 
	 * @param name
	 *            the name of the property to set.
	 * @param value
	 *            the value to set.
	 */
	void setProperty(String name, String value);
}
