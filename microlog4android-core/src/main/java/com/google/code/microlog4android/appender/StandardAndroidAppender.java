package com.google.code.microlog4android.appender;

import java.io.IOException;

import android.util.Log;

import com.google.code.microlog4android.Level;

public class StandardAndroidAppender extends AbstractAppender {

	@Override
	public void clear() {
	}


	@Override
	public void doLog(String clientID, String name, long time, Level level, Object message, Throwable t) {

		if (logOpen && formatter != null) {
						
			switch (level) {
			case FATAL:
			case ERROR:
				Log.e(name, formatter.format(clientID, name, time, level, message, t));
				break;
			
			case WARN:
				Log.w(name, formatter.format(clientID, name, time, level, message, t));
				break;
			
			case INFO:
				Log.i(name, formatter.format(clientID, name, time, level, message, t));
				break;
				
			case DEBUG:
			case TRACE:
				Log.d(name, formatter.format(clientID, name, time, level, message, t));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void open() throws IOException {
		logOpen = true;
	}

	@Override
	public void close() throws IOException {
		logOpen = false;
	}
	
	@Override
	public long getLogSize() {
		return SIZE_UNDEFINED;
	}

}
