/*
 * Copyright 2010 The Microlog project @sourceforge.net
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;

import com.google.code.microlog4android.Appender;
import com.google.code.microlog4android.Level;

/**
 * An appender to log to a file in on the SDCard.
 * 
 * @author Johan Karlsson
 * 
 */
public class FileAppender extends AbstractAppender {

	public static final String DEFAULT_FILENAME = "microlog.txt";

	private String fileName = DEFAULT_FILENAME;

	private PrintWriter writer;

	private boolean append = false;

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#open()
	 */
	@Override
	public void open() throws IOException {
		
		File logFile = getSDCardFile();
		FileOutputStream fileOutputStream = null;

		if (logFile != null) {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fileOutputStream = new FileOutputStream(logFile, append);
		}

		if (fileOutputStream != null) {
			writer = new PrintWriter(fileOutputStream);
		} else {
			System.err.println("Failed to create the log file (no stream)");
			logOpen = false;
		}

		logOpen = true;
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#clear()
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#close()
	 */
	@Override
	public void close() throws IOException {
		System.out.println("Closing the FileAppender");
		if (writer != null) {
			writer.close();
		}
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#doLog(java.lang.String,
	 *      java.lang.String, long, com.google.code.microlog4android.Level,
	 *      java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void doLog(String clientID, String name, long time, Level level,
			Object message, Throwable throwable) {
		if (logOpen && formatter != null && writer != null) {
			writer.println(formatter.format(clientID, name, time, level,
					message, throwable));
			writer.flush();

			if (throwable != null) {
				throwable.printStackTrace();
			}
		} else if (formatter == null) {
			System.err.println("Please set a formatter.");
		}

	}

	/**
	 * @see com.google.code.microlog4android.Appender#getLogSize()
	 */
	public long getLogSize() {
		return Appender.SIZE_UNDEFINED;
	}

	/**
	 * Set the filename to be used
	 * 
	 * @param fileName
	 *            the filename to log to
	 */
	public void setFileName(String fileName) {
		// TODO Throw IllegalArgumentException if the filename is null.
		if (fileName != null) {
			this.fileName = fileName;
		}
	}

	/**
	 * Set if we shall append the file when logging or if we shall start over
	 * all again when starting a new session.
	 * 
	 * @param append
	 *            the append to set
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	private File getSDCardFile() {
		String externalStorageState = Environment.getExternalStorageState();
		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		File file = null;

		if (externalStorageState.equals(Environment.MEDIA_MOUNTED)
				&& externalStorageDirectory != null) {
			file = new File(externalStorageDirectory, fileName);
		}

		return file;
	}

}
