package com.google.code.microlog4android;

public enum Level {
	FATAL(Level.FATAL_INT),
	ERROR(Level.ERROR_INT),
	WARN(Level.WARN_INT),
	INFO(Level.INFO_INT),
	DEBUG(Level.DEBUG_INT),
	TRACE(Level.TRACE_INT),
	OFF(Level.OFF_INT);
	
	public static final int FATAL_INT = 16;
	public static final int ERROR_INT = 8;
	public static final int WARN_INT = 4;
	public static final int INFO_INT = 2;
	public static final int DEBUG_INT = 1;
	public static final int TRACE_INT = 0;
	public static final int OFF_INT = -1;
	
	private final int levelValue;
	
	private Level(final int levelValue) {
		this.levelValue = levelValue;
	}
	
	public int toInt() {
		return levelValue;
	}
	
	public String toString() {
		return this.name();
	}
}
