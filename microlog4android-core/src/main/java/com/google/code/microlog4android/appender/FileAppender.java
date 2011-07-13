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
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.google.code.microlog4android.Level;

/**
 * An appender to log to a file in on the SDCard.
 * 
 * @author Johan Karlsson
 * @author Dan Walkes
 * 
 */
public class FileAppender extends AbstractAppender {
	private static final String TAG = "Microlog.FileAppender";
	
	public static final String DEFAULT_FILENAME = "microlog.txt";
	
	private String fileName = DEFAULT_FILENAME;

	private PrintWriter writer;

	private boolean append = false;
	
	private File mSdCardLogFile = null;
	
	Context mContext = null;
	
	/**
	 * Create a file appender using the specified application context.
	 * Note: your application must hold android.permission.WRITE_EXTERNAL_STORAGE
	 * to be able to access the SDCard.
	 * @param c
	 */
	public FileAppender(Context c) {
		mContext = c;
	}

	/**
	 * Create a file appender without application context.  The logging file will
	 * be placed in the root folder and will not be removed when your application is
	 * removed.  Use FileAppender(Context) to create a log that is automatically removed
	 * when your application is removed
	 * Note: your application must hold android.permission.WRITE_EXTERNAL_STORAGE
	 * to be able to access the SDCard.
	 */
	public FileAppender() {
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#open()
	 */
	@Override
	public synchronized void open() throws IOException {
		File logFile = getLogFile();
		logOpen = false;

		if (logFile != null) {
			if (!logFile.exists()) {
				if(!logFile.createNewFile()) {
					Log.e(TAG, "Unable to create new log file");
					
				}
			}
			
			FileOutputStream fileOutputStream = new FileOutputStream(logFile, append);
			
			if(fileOutputStream != null) {
				writer = new PrintWriter(fileOutputStream);
				logOpen = true;
			} else {
				Log.e(TAG, "Failed to create the log file (no stream)");
			}
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
	public synchronized void close() throws IOException {
		Log.i(TAG, "Closing the FileAppender");
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
	public synchronized void doLog(String clientID, String name, long time, Level level,
			Object message, Throwable throwable) {
		if (logOpen && formatter != null && writer != null) {
			writer.println(formatter.format(clientID, name, time, level,
					message, throwable));
			writer.flush();

			if (throwable != null) {
				throwable.printStackTrace();
			}
		} else if (formatter == null) {
			Log.e(TAG, "Please set a formatter.");
		}

	}

	/**
	 * @see com.google.code.microlog4android.appender.Appender#getLogSize()
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
	 *            the append to set (default = false)
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	/**
	 * Android 1.6-2.1 used {@link Environment#getExternalStorageDirectory()} 
	 *  to return the (root)
	 * external storage directory.  Folders in this subdir were shared by all applications
	 * and were not removed when the application was deleted.
	 * Starting with andriod 2.2, Context.getExternalFilesDir() is available.  
	 * This is an external directory available to the application which is removed when the application
	 * is removed.
	 * 
	 * This implementation uses Context.getExternalFilesDir() if available, if not available uses 
	 * {@link Environment#getExternalStorageDirectory()}. 
	 * 
	 * @return a File object representing the external storage directory
	 * used by this device or null if the subdir could not be created or proven to exist
	 */
	protected synchronized File getExternalStorageDirectory() {

		File externalStorageDirectory = Environment
			.getExternalStorageDirectory();
		
		int sdk = new Integer(Build.VERSION.SDK);
		if(sdk>=8 && mContext!= null) {
			Method getExtFilesDir;
			try {
				/*
				 * Use indirection to invoke getExternalFilesDir(), this allows us to
				 * support platforms less than 8.
				 */
				getExtFilesDir = Context.class.getMethod("getExternalFilesDir",new Class[] {String.class});
				/*
				 * Invoke getExtFilesDir with null argument
				 */
				externalStorageDirectory = (File) getExtFilesDir.invoke(mContext,new Object[] {null});
			}
			catch (Throwable t) {
				Log.e(TAG, "Could not execute method getExternalFilesDir() on sdk >=8",t);
			}
		}
		if( externalStorageDirectory != null) {
			if(!externalStorageDirectory.exists()) {
				if(!externalStorageDirectory.mkdirs()) {
					externalStorageDirectory = null;
					Log.e(TAG, "mkdirs failed on externalStorageDirectory " + externalStorageDirectory);
				}
			}
		}
		return externalStorageDirectory;
	}
	
	/**
	 * @return the log file used to log to external storage
	 */
	public synchronized File getLogFile() {
		
		if( mSdCardLogFile == null ) {
			String externalStorageState = Environment.getExternalStorageState();
			if(externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
				File externalStorageDirectory = getExternalStorageDirectory();
		
				if (externalStorageDirectory != null) {
					mSdCardLogFile = new File(externalStorageDirectory, fileName);
				}
			}
			
			if(mSdCardLogFile == null) {
				Log.e(TAG, "Unable to open log file from external storage");
			}
		}

		return mSdCardLogFile;
	}

}
