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
 * A class that works as a stop watch.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 */
class StopWatch {

	private boolean started;
	private long startTime;
	private long currentTime;
	private long stopTime;

	/**
	 * Start the <code>StopWatch</code>.
	 */
	public synchronized void start() {
		this.startTime = System.currentTimeMillis();
		started = true;
	}

	/**
	 * Get the current time.
	 * 
	 * @return the current time as milliseconds since the <code>StopWatch</code>
	 *         was started.
	 */
	public synchronized long getCurrentTime() {

		if (started) {
			currentTime = System.currentTimeMillis() - startTime;
		}

		return currentTime;
	}

	/**
	 * Stop the <code>StopWatch</code>.
	 * 
	 * @return the current time when
	 */
	public synchronized long stop() {

		stopTime = System.currentTimeMillis();
		currentTime = stopTime - startTime;
		started = false;

		return currentTime;
	}

	/**
	 * Reset the <code>StopWatch</code>.
	 */
	public synchronized void reset() {

		if (started) {
			startTime = System.currentTimeMillis();
		} else {
			startTime = 0;
		}

		stopTime = 0;
		currentTime = 0;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.valueOf(getCurrentTime());
	}

}
