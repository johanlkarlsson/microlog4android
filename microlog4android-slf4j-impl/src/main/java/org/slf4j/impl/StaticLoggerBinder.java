package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.impl.repository.Slf4jLoggerRepository;
import org.slf4j.spi.LoggerFactoryBinder;

public enum StaticLoggerBinder implements LoggerFactoryBinder{
	SINGLETON;

	/**
	 * Return the singleton of this class.
	 * 
	 * @return the StaticLoggerBinder singleton
	 */
	public static final StaticLoggerBinder getSingleton() {
		return SINGLETON;
	}

	/**
	 * Declare the version of the SLF4J API this implementation is compiled
	 * against. The value of this field is usually modified with each release.
	 */
	// to avoid constant folding by the compiler, this field must *not* be final
	public static String REQUESTED_API_VERSION = "1.5.11"; // !final

	private static final String loggerFactoryClassStr = Slf4jLoggerRepository.class.getName();

	/**
	 * The ILoggerFactory instance returned by the {@link #getLoggerFactory}
	 * method should always be the same object
	 */
	private final ILoggerFactory loggerFactory;

	private StaticLoggerBinder() {
		loggerFactory = Slf4jLoggerRepository.INSTANCE;
	}

	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	public String getLoggerFactoryClassStr() {
		return loggerFactoryClassStr;
	}
}
