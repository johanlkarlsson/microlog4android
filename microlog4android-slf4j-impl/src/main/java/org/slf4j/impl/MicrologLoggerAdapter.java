package org.slf4j.impl;

import org.slf4j.helpers.MarkerIgnoringBase;

import com.google.code.microlog4android.Logger;

/**
 * 
 * @author Jarle Hansen (hansjar@gmail.com)
 *
 */
public class MicrologLoggerAdapter extends MarkerIgnoringBase {
	private static final long serialVersionUID = 3934653965724860568L;
	
	private final Logger logger;
	
	MicrologLoggerAdapter(final Logger logger) {
		this.logger = logger;
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(final String msg) {
		logger.trace(msg);
	}

	public void trace(final String format, final Object param1) {
		// TODO trace format + object
	}

	public void trace(final String format, final Object param1, final Object param2) {
		// TODO trace format + multiple objects
	}
	
	public void trace(final String format, final Object[] argArray) {
		// TODO trace format + object array
	}

	public void trace(final String msg, final Throwable t) {
		logger.trace(msg, t);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(final String msg) {
		logger.debug(msg);
	}

	public void debug(final String format, final Object arg1) {
		// TODO debug format + object
	}

	public void debug(final String format, final Object param1, final Object param2) {
		// TODO debug format + multiple objects
	}

	public void debug(final String format, final Object[] argArray) {
		// TODO debug format + object array
	}

	public void debug(final String msg, final Throwable t) {
		logger.debug(msg, t);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(final String msg) {
		logger.info(msg);
	}

	public void info(final String format, final Object arg) {
		// info format + object
	}

	public void info(final String format, final Object arg1, final Object arg2) {
		// TODO info format + multiple objects
	}

	public void info(final String format, final Object[] argArray) {
		// TODO info format + object array
	}

	public void info(final String msg, final Throwable t) {
		logger.info(msg, t);
	}

	public boolean isWarnEnabled() {
		return true; // TODO why is there no isWarnEnabled in the microlog logger?
	}

	public void warn(final String msg) {
		logger.warn(msg);
	}

	public void warn(final String format, final Object arg) {
		// TODO warn format + object
	}

	public void warn(final String format, final Object arg1, final Object arg2) {
		// TODO warn format + multiple objects
	}

	public void warn(final String format, final Object[] argArray) {
		// TODO warn format + object array
	}

	public void warn(final String msg, final Throwable t) {
		logger.warn(msg, t);
	}

	public boolean isErrorEnabled() {
		return true; // why is there no isErrorEnabled in the microlog logger?
	}

	public void error(final String msg) {
		logger.error(msg);
	}

	public void error(final String format, final Object arg) {
		// TODO error format + object
	}

	public void error(final String format, final Object arg1, final Object arg2) {
		// TODO error format + multiple objects
	}

	public void error(final String format, final Object[] argArray) {
		// TODO error format + object array
	}

	public void error(final String msg, final Throwable t) {
		logger.error(msg, t);
	}

	/**
	 * For formatted messages substitute arguments.
	 * 
	 * @param format
	 * @param arg1
	 * @param arg2
	 */
//	private String format(final String format, final Object arg1, final Object arg2) {
//		return MessageFormatter.format(format, arg1, arg2);
//	}

	/**
	 * For formatted messages substitute arguments.
	 * 
	 * @param format
	 * @param args
	 */
//	private String format(final String format, final Object[] args) {
//		return MessageFormatter.arrayFormat(format, args);
//	}
}
