package com.google.code.microlog4android.factory;

import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.appender.LogCatAppender;
import com.google.code.microlog4android.format.PatternFormatter;

public enum DefaultAppenderFactory {
	;
	
	public static Appender createDefaultAppender() {
		Appender appender = new LogCatAppender();
		appender.setFormatter(new PatternFormatter());
		
		return appender;
	}
}
