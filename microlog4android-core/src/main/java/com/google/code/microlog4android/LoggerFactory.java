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
package com.google.code.microlog4android;

import com.google.code.microlog4android.factory.DefaultRepositoryFactory;
import com.google.code.microlog4android.repository.LoggerRepository;



/**
 * This is the public logger factory to be used by the end user.
 * 
 * @author Johan Karlsson
 * 
 */
public class LoggerFactory {
	
	/**
	 * The reference to the underlying logger repository.
	 */
	private static final LoggerRepository loggerRepository = DefaultRepositoryFactory.getDefaultLoggerRepository();

	/**
	 * The un-named logger. This is the classic Microlog method, that is
	 * used when no logger hierarchies are wanted.
	 * 
	 * @return the <code>Logger</code> object.
	 */
	public static Logger getLogger() {
		return loggerRepository.getRootLogger();
	}

	/**
	 * Get a <code>Logger</code> object with the specified name.
	 * 
	 * @param name
	 *            the name of the logger.
	 * @return the <code>Logger</code> object.
	 */
	public static Logger getLogger(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"The Logger name must not be null.");
		}
		
		return loggerRepository.getLogger(name);
	}

	/**
	 * Get a <code>Logger</code> object that corresponds to the specified class.
	 * This is a convenience method, which is the same as a call to
	 * <code>LoggerFactory.getLogger(clazz.getName());</code>
	 * 
	 * @param clazz
	 *            the <code>Class</code> object to get the name from.
	 * @return the <code>Logger</code> object.
	 */
	public static Logger getLogger(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("The clazz must not be null.");
		}

		return LoggerFactory.getLogger(clazz.getName());
	}

	/**
	 * Shutdown Microlog.
	 */
	public static void shutdown() {
		loggerRepository.shutdown();
	}

	/**
	 * Get the <code>LoggerRepository</code> that is used for the
	 * <code>LoggerFactory</code>.
	 * 
	 * @return the <code>LoggerRepository</code>.
	 */
	public static LoggerRepository getLoggerRepository() {
		return LoggerFactory.loggerRepository;
	}
}
