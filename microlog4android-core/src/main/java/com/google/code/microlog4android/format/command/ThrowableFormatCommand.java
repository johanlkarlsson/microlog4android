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
 * Converts the <code>Throwable</code> to a message.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 */
public class ThrowableFormatCommand implements FormatCommandInterface {
	
	/**
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#init(String)
	 */
	public void init(String initString) {
		// Do nothing.	
	}

	/**
	 * Set the log data.
	 * 
	 * @see FormatCommandInterface#execute(String, String, long, Level, Object, Throwable)
	 */
	public String execute(String clientID, String name, long time, Level level, Object message, Throwable throwable) {

		StringBuilder sb = new StringBuilder();
		if (throwable != null) {
			sb.append(throwable.toString());
			String newline = System.getProperty("line.separator");
			StackTraceElement[] stackTrace = throwable.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(newline);
				sb.append("\tat ");
				sb.append(element.toString());
			}
		}

		return sb.toString();
	}

}
