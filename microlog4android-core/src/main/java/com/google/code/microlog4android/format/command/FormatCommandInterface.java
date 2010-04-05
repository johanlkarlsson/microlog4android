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

package com.google.code.microlog4android.format.command;

import com.google.code.microlog4android.Level;

/**
 * An interface for (pattern) format command objects.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 */
public interface FormatCommandInterface {

	/**
	 * Set a <code>String</code> that is not formatted.
	 * 
	 * @param initString
	 *            the <code>String</code> to be used for initialization.
	 */
	public void init(String initString);

	/**
	 * Set the necessary log data to convert.
	 * 
	 * @param clientID
	 *            the client id.
	 * @param name
	 *            the name of the logger.
	 * @param time
	 *            the time since the first logging has done (in milliseconds).
	 * @param level
	 *            the log level.
	 * @param message
	 *            the log message.
	 * @param throwable
	 *            the logged <code>Throwable</code> object.
	 * 
	 * @return a converted <code>String</code>.
	 */
	public String execute(String clientID, String name, long time, Level level,
			Object message, Throwable throwable);
}
