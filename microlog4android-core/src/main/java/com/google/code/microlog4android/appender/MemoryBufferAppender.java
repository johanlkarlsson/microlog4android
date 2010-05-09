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
package com.google.code.microlog4android.appender;

import java.io.IOException;
import java.util.Vector;

import android.util.Log;

import com.google.code.microlog4android.CyclicBuffer;
import com.google.code.microlog4android.Level;


/**
 * An appender that writes the log entries to a memory buffer. It can be
 * configured to hold a maximum number of entries, which by default is set to 20
 * and it can run in two different modes: either it stops logging when the
 * buffer limit is reached or the oldest log entry is overwritten. By default it
 * overwrites old log entries.
 * 
 * The appender supports two different configuration options:
 * <table border="1">
 * <tr>
 * <th>Configuration key</th>
 * <th>Values</th>
 * <th>Default value</th>
 * </tr>
 * <tr>
 * <td>microlog.appender.MemoryBufferAppender.cyclicBuffer</td>
 * <td>true or false</td>
 * <td>true</td>
 * </tr>
 * <tr>
 * <td>microlog.appender.MemoryBufferAppender.maxLogEntries</td>
 * <td>Max number of entries</td>
 * <td>20</td>
 * </tr>
 * </table>
 * 
 * @author Henrik Larne (henriklarne@users.sourceforge.net)
 * @since 1.1.0
 */
public class MemoryBufferAppender extends AbstractAppender {
	private static final String TAG = "Microlog.MemoryBufferAppender";
	
	public static final String MAX_LOG_ENTRIES_PROPERTY = "maxLogEntries";

	public static final String CYCLIC_BUFFER_PROPERTY = "cyclicBuffer";

	public static final String[] PROPERTY_NAMES = { CYCLIC_BUFFER_PROPERTY,
			MAX_LOG_ENTRIES_PROPERTY };

	/**
	 * The default maximum number of log entries
	 */
	private static final int DEFAULT_MAX_NBR_OF_ENTRIES = 20;

	private CyclicBuffer cyclicBuffer;

	/**
	 * Is the buffer cyclic or does it stop logging when it gets full
	 */
	private boolean cyclicBufferEnabled;

	/**
	 * Create a <code>MemoryBufferAppender</code> with the default settings
	 */
	public MemoryBufferAppender() {
		this(DEFAULT_MAX_NBR_OF_ENTRIES, true);
	}

	/**
	 * Create a customized <code>MemoryBufferAppender</code>
	 * 
	 * @param maxNbrOfEntries
	 *            the maximum number of entries to hold in the buffer
	 * @param cyclicBufferEnabled
	 *            should the buffer be cyclic or stop logging when it gets full
	 */
	public MemoryBufferAppender(int maxNbrOfEntries, boolean cyclicBufferEnabled) {
		if (maxNbrOfEntries <= 0) {
			maxNbrOfEntries = DEFAULT_MAX_NBR_OF_ENTRIES;
		}

		this.cyclicBufferEnabled = cyclicBufferEnabled;
		this.cyclicBuffer = new CyclicBuffer(maxNbrOfEntries);
	}

	/**
	 * Get the maximum number of entries that this appender is configured for
	 * 
	 * @return the maximum number of entries the appender can hold
	 */
	public int getMaxNbrOfEntries() {
		return cyclicBuffer.getBufferSize();
	}

	/**
	 * Is the buffer cyclic or fixed
	 * 
	 * @return true, if the buffer is cyclic, false otherwise
	 */
	public boolean isCyclicBuffer() {
		return cyclicBufferEnabled;
	}

	/**
	 * Retrieve the log buffer. Note that this is the actual buffer being used
	 * for logging, thus do not alter it.
	 * 
	 * @return the log buffer containing the log entries as Strings
	 */
	public Vector getLogBuffer() {
		return cyclicBuffer.getAsVector();
	}

	/**
	 * Do the logging.
	 * 
	 * @param level
	 *            the level at which the logging shall be done.
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the exception to log.
	 */
	public void doLog(String clientID, String name, long time, Level level,
			Object message, Throwable t) {
		if (logOpen && formatter != null) {
			// Check if the buffer is full
			if (cyclicBuffer.length() >= cyclicBuffer.getBufferSize()) {
				// The buffer is full, should we replace the oldest log entry or
				// disregard the log entry
				if (cyclicBufferEnabled) {
					cyclicBuffer.add(formatter.format(clientID, name, time,
							level, message, t));
				}
			} else {
				// The buffer is not full, simply add the log entry
				cyclicBuffer.add(formatter.format(clientID, name, time, level,
						message, t));
			}
		} else if (formatter == null) {
			Log.e(TAG, "Please set a formatter.");
		}
	}

	/**
	 * Clear the buffer of all log entries
	 * 
	 * @see com.google.code.microlog4android.appender.AbstractAppender#clear()
	 */
	public void clear() {
		cyclicBuffer.clear();
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#close()
	 */
	public void close() throws IOException {
		logOpen = false;
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#open()
	 */
	public void open() throws IOException {
		logOpen = true;
	}

	/**
	 * Get the current size of the log
	 * 
	 * @return the size of the log
	 */
	public long getLogSize() {
		return cyclicBuffer.length();
	}

}
