package com.google.code.microlog4android.integration.tests.appender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.format.Formatter;

public class JunitTestAppender implements Appender {
	private List<String> loggerList = new ArrayList<String>();

	@Override
	public void clear() {
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void doLog(String clientID, String name, long time, Level level, Object message, Throwable t) {
		loggerList.add((String)message);
	}

	@Override
	public Formatter getFormatter() {
		return null;
	}

	@Override
	public long getLogSize() {
		return 0;
	}

	@Override
	public boolean isLogOpen() {
		return false;
	}

	@Override
	public void open() throws IOException {
	}

	@Override
	public void setFormatter(Formatter arg0) {
	}
	
	public List<String> getLoggerList() {
		return loggerList;
	}
}
