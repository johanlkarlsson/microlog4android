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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.factory.DefaultAppenderFactory;
import com.google.code.microlog4android.repository.CommonLoggerRepository;


/**
 * The <code>Logger</code> class is used for logging.
 * 
 * This is similar to the Log4j <code>Logger</code> class. The method names are
 * the same, but we do not claim that they are 100% compatible.
 * 
 * You have the ability to use named loggers as in Log4j. If you want to save
 * memory, you could use unnamed loggers.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @author Darius Katz
 * @author Karsten Ohme
 * @since 0.1
 */
public final class Logger {
	private static final String TAG = "Microlog.Logger";

	public static final Level DEFAULT_LOG_LEVEL = Level.DEBUG;

	public static final String DEFAULT_CLIENT_ID = "Microlog";

	private CommonLoggerRepository commonLoggerRepository = null;

	private String clientID = DEFAULT_CLIENT_ID;

	private String name;

	private Level level;

	private static final StopWatch stopWatch = new StopWatch();

	private final static List<Appender> appenderList = new ArrayList<Appender>(4);

	private static boolean firstLogEvent = true;

	/**
	 * Create a logger with the specified <code>name</code>. The
	 * <code>LoggerFactory</code> should be used for creating
	 * <code>Logger</code> objects.
	 * 
	 * @param name
	 *            the name of the logger.
	 */
	public Logger(String name) {
		this.name = name;
	}
	
	public Logger(String name, CommonLoggerRepository commonLoggerRepository) {
		this.name = name;
		this.commonLoggerRepository = commonLoggerRepository;
	}

	public synchronized void setCommonRepository(final CommonLoggerRepository commonLoggerRepository) {
		this.commonLoggerRepository = commonLoggerRepository;
	}

	/**
	 * Get the log level.
	 * 
	 * @return the log <code>Level</code>.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Set the log level.
	 * 
	 * @param level
	 *            The logLevel to set.
	 * @throws IllegalArgumentException
	 *             if the <code>level</code> is null.
	 */
	public void setLevel(Level level) throws IllegalArgumentException {
		if (level == null) {
			throw new IllegalArgumentException("The level must not be null.");
		}
		this.level = level;
	}

	/**
	 * Get the effective log level. If we have a hierarchy of loggers, this is
	 * searched to get the effective level.
	 * 
	 * @return the effective logger level.
	 */
	public Level getEffectiveLevel() {
		Level effectiveLevel = level;

		if (effectiveLevel == null && !name.equals("")) {
			if(commonLoggerRepository == null) {
				throw new IllegalStateException("CommonLoggerRepository has not been set");
			} else {
				effectiveLevel = commonLoggerRepository.getEffectiveLevel(name);
			}
		}

		return effectiveLevel;
	}

	/**
	 * Get the client ID.
	 * 
	 * @return the clientID
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * Set the client ID.
	 * 
	 * @param clientID
	 *            the clientID to set
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	/**
	 * Get the name of the <code>Logger</code>.
	 * 
	 * @return the name of the <code>Logger</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Add the specified appender to the output appenders.
	 * 
	 * @param appender
	 *            the <code>Appender</code> to add.
	 * @throws IllegalArgumentException
	 *             if the <code>appender</code> is <code>null</code>.
	 */
	public void addAppender(Appender appender) throws IllegalArgumentException {
		if (appender == null) {
			throw new IllegalArgumentException("Appender not allowed to be null");
		}

		if (!appenderList.contains(appender)) {
			appenderList.add(appender);
		}
	}

	/**
	 * Remove the specified appender from the appender list.
	 * 
	 * @param appender
	 *            the <code>Appender</code> to remove.
	 */
	public void removeAppender(Appender appender) throws IllegalArgumentException {
		if (appender == null) {
			throw new IllegalArgumentException("The appender must not be null.");
		}

		if (appender.isLogOpen()) {
			try {
				appender.close();
			} catch (IOException e) {
				Log.e(TAG, "Failed to close appender. " + e);
			}
		}
		appenderList.remove(appender);
	}

	/**
	 * Remove all the appenders.
	 * 
	 */
	public void removeAllAppenders() {
		for (Appender appender : appenderList) {
			if (appender.isLogOpen()) {
				try {
					appender.close();
				} catch (IOException e) {
					Log.e(TAG, "Failed to close appender. " + e);
				}
			}
		}
		appenderList.clear();
	}

	/**
	 * Get the number of appenders.
	 * 
	 * @return the number of appenders.
	 */
	public int getNumberOfAppenders() {
		return appenderList.size();
	}

	/**
	 * Get the specified appender, starting at index = 0.
	 * 
	 * @param index
	 *            the index of the appender.
	 * @return the appender.
	 */
	public Appender getAppender(int index) {
		return appenderList.get(index);
	}

	/**
	 * Log the message at the specified level.
	 * 
	 * @param level
	 *            the <code>Level</code> to log at.
	 * @param message
	 *            the message to log.
	 * @throws IllegalArgumentException
	 *             if the <code>level</code> is <code>null</code>.
	 */
	public void log(Level level, Object message) throws IllegalArgumentException {
		log(level, message, null);
	}

	/**
	 * Log the message and the Throwable object at the specified level.
	 * 
	 * @param level
	 *            the log level
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the <code>Throwable</code> object.
	 * @throws IllegalArgumentException
	 *             if the <code>level</code> is <code>null</code>.
	 */
	public void log(Level level, Object message, Throwable t) throws IllegalArgumentException {
		if (level == null) {
			throw new IllegalArgumentException("The level must not be null.");
		}

		if (getEffectiveLevel().toInt() <= level.toInt() && level.toInt() > Level.OFF_INT) {
			
			if (firstLogEvent == true) {
				addDefaultAppender();

				try {
					open();
				} catch (IOException e) {
					Log.e(TAG, "Failed to open the log. " + e);
				}

				stopWatch.start();
				firstLogEvent = false;
			}

			for (Appender appender : appenderList) {
				appender.doLog(clientID, name, stopWatch.getCurrentTime(), level, message, t);
			}
		}
	}
	
	private void addDefaultAppender() {
		if(appenderList.size() == 0) {
			Log.w(TAG, "Warning! No appender is set, using LogCatAppender with PatternFormatter");
			Appender appender = DefaultAppenderFactory.createDefaultAppender();
			addAppender(appender);
		}
	}

	/**
	 * Is this <code>Logger</code> enabled for TRACE level?
	 * 
	 * @return true if logging is enabled.
	 */
	public boolean isTraceEnabled() {
		Level effectiveLevel = getEffectiveLevel();
		return effectiveLevel.toInt() <= Level.TRACE_INT;
	}

	/**
	 * Log the message at <code>Level.TRACE</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void trace(Object message) {
		log(Level.TRACE, message, null);
	}

	/**
	 * Log the message and the <code>Throwable</code> object at
	 * <code>Level.TRACE</code>.
	 * 
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the Throwable object to log.
	 */
	public void trace(Object message, Throwable t) {
		log(Level.TRACE, message, t);
	}

	/**
	 * Is this <code>Logger</code> enabled for DEBUG level?
	 * 
	 * @return true if logging is enabled.
	 */
	public boolean isDebugEnabled() {
		Level effectiveLevel = getEffectiveLevel();
		return effectiveLevel.toInt() <= Level.DEBUG_INT;
	}

	/**
	 * Log the message at <code>Level.DEBUG</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void debug(Object message) {
		log(Level.DEBUG, message, null);
	}

	/**
	 * Log the message and the <code>Throwable</code> object at
	 * <code>Level.DEBUG</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the <code>Throwable</code> object to log.
	 */
	public void debug(Object message, Throwable t) {
		log(Level.DEBUG, message, t);
	}

	/**
	 * Is this <code>Logger</code> enabled for INFO level?
	 * 
	 * @return true if the <code>Level.INFO</code> level is enabled.
	 */
	public boolean isInfoEnabled() {
		Level effectiveLevel = getEffectiveLevel();
		return effectiveLevel.toInt() <= Level.INFO_INT;
	}

	/**
	 * Log the specified message at <code>Level.INFO</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void info(Object message) {
		log(Level.INFO, message, null);
	}

	/**
	 * Log the specified message and the <code>Throwable</code> at
	 * <code>Level.INFO</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the <code>Throwable</code> to log.
	 */
	public void info(Object message, Throwable t) {
		log(Level.INFO, message, t);
	}

	/**
	 * Log the specified message at <code>Level.WARN</code> level.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void warn(Object message) {
		log(Level.WARN, message, null);
	}

	/**
	 * Log the specified message and <code>Throwable</code> object at
	 * <code>Level.WARN</code> level.
	 * 
	 * @param message
	 *            the object to log.
	 * @param t
	 *            the <code>Throwable</code> to log.
	 */
	public void warn(Object message, Throwable t) {
		log(Level.WARN, message, t);
	}

	/**
	 * Log the specified message at ERROR level.
	 * 
	 * @param message
	 *            the object to log.
	 */
	public void error(Object message) {
		log(Level.ERROR, message, null);
	}

	/**
	 * Log the specified message and Throwable object at ERROR level.
	 * 
	 * @param message
	 *            the object to log.
	 * @param t
	 *            the <code>Throwable</code> to log.
	 */
	public void error(Object message, Throwable t) {
		log(Level.ERROR, message, t);
	}

	/**
	 * Log the specified message at FATAL level.
	 * 
	 * @param message
	 *            the object to log.
	 */
	public void fatal(Object message) {
		log(Level.FATAL, message, null);
	}

	/**
	 * Log the specified message and Throwable object at FATAL level.
	 * 
	 * @param message
	 *            the object to log.
	 * @param t
	 *            the <code>Throwable</code> to log.
	 */
	public void fatal(Object message, Throwable t) {
		log(Level.FATAL, message, t);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(super.toString());
		stringBuffer.append('[');

		for (Appender appender : appenderList) {
			stringBuffer.append(appender);
			stringBuffer.append(';');
		}

		stringBuffer.append(']');
		return stringBuffer.toString();
	}

	/**
	 * Reset the Logger, i.e. remove all appenders and set the log level to the
	 * default level.
	 */
	public synchronized void resetLogger() {
		Logger.appenderList.clear();
		Logger.stopWatch.stop();
		Logger.stopWatch.reset();
		Logger.firstLogEvent = true;
	}

	/**
	 * Open the log. The logging is now turned on.
	 */
	void open() throws IOException {
		for (Appender appender : appenderList) {
			appender.open();
		}
	}

	/**
	 * Close the log. From this point on, no logging is done.
	 * 
	 * @throws IOException
	 *             if the <code>Logger</code> failed to close.
	 */
	public void close() throws IOException {
		for (Appender appender : appenderList) {
			appender.close();
		}

		stopWatch.stop();
		Logger.firstLogEvent = true;
	}
}
