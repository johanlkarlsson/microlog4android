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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;

import com.google.code.microlog4android.Appender;
import com.google.code.microlog4android.Level;


/**
 * An appender to log to a file in the application private dir.
 * 
 * @author Johan Karlsson
 * 
 */
public class FileAppender extends AbstractAppender {

	public static final String DEFAULT_FILENAME = "microlog.txt";

	private Context context;

	private String fileName = DEFAULT_FILENAME;

	private PrintWriter writer;

	/**
	 * Set the {@link Context} for the {@link FileAppender}
	 * 
	 * Note: this must be set in order to create a file.
	 * 
	 * @param context
	 *            the application context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Set the filename to be used
	 * 
	 * @param fileName
	 *            the filename to log to
	 */
	public void setFileName(String fileName) {
		if (fileName != null) {
			this.fileName = fileName;
		}
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
		if (logOpen && formatter != null) {
			writer.println(formatter.format(clientID, name, time, level,
					message, throwable));

			if (throwable != null) {
				throwable.printStackTrace();
			}
		} else if (formatter == null) {
			System.err.println("Please set a formatter.");
		}

	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#open()
	 */
	@Override
	public void open() throws IOException {
		FileOutputStream outputStream = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
		writer = new PrintWriter(outputStream);
	}

	/**
	 * @see com.google.code.microlog4android.Appender#getLogSize()
	 */
	public long getLogSize() {
		return Appender.SIZE_UNDEFINED;
	}

}
