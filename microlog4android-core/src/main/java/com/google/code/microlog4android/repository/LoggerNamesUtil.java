package com.google.code.microlog4android.repository;


public enum LoggerNamesUtil {
	;
	
	private static final String EMPTY = "";
	
	public static String[] getLoggerNameComponents(final String loggerName) {
		return loggerName.split("\\.");
	}
	
	public static String getClassName(final String[] pathComponents) {
		final String classNames;
		if(pathComponents.length > 0) {
			classNames = pathComponents[pathComponents.length -1];
		} else {
			classNames = EMPTY;
		}
		
		return classNames;
	}
}
