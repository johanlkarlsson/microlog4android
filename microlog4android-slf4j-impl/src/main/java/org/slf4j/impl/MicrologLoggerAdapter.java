package org.slf4j.impl;

import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.impl.repository.Slf4jLoggerRepository;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;

/**
 * 
 * @author Jarle Hansen (hansjar@gmail.com)
 *
 */
public class MicrologLoggerAdapter extends MarkerIgnoringBase {
	private static final long serialVersionUID = 3934653965724860568L;
	
	// This is deserialized by the readResolve method in the NamedLoggerBase class
	private transient final Logger logger;
	
	public MicrologLoggerAdapter(final Logger logger) {
		this.logger = logger;
		this.name = logger.getName();
		logger.setCommonRepository(Slf4jLoggerRepository.INSTANCE);
	}

	public MicrologLoggerAdapter(final String name) {
		logger = new Logger(name, Slf4jLoggerRepository.INSTANCE);
		this.name = name;
	}
	
	public Logger getMicrologLogger() {
		return logger;
	}
	
	public String getName() {
		return logger.getName();
	}
	
	public boolean isTraceEnabled() {
		return isLoggerEnabled(Level.TRACE);
	}

	public void trace(final String msg) {
		logger.trace(msg);
	}

	public void trace(final String format, final Object param1) {
		// TODO trace format + object
		throw new UnsupportedOperationException("trace(String, Object) is not implemented yet");
	}

	public void trace(final String format, final Object param1, final Object param2) {
		// TODO trace format + multiple objects
		throw new UnsupportedOperationException("trace(String, Object, Object) is not implemented yet");
	}
	
	public void trace(final String format, final Object[] argArray) {
		// TODO trace format + object array
		throw new UnsupportedOperationException("trace(String, Object[]) is not implemented yet");
	}

	public void trace(final String msg, final Throwable t) {
		logger.trace(msg, t);
	}
	
	public boolean isDebugEnabled() {
		return isLoggerEnabled(Level.DEBUG);
	}

	public void debug(final String msg) {
		logger.debug(msg);
	}

	public void debug(final String format, final Object arg1) {
		// TODO debug format + object
		throw new UnsupportedOperationException("debug(String, Object) is not implemented yet");
	}

	public void debug(final String format, final Object param1, final Object param2) {
		// TODO debug format + multiple objects
		throw new UnsupportedOperationException("debug(String, Object, Object) is not implemented yet");
	}

	public void debug(final String format, final Object[] argArray) {
		// TODO debug format + object array
		throw new UnsupportedOperationException("debug(String, Object[]) is not implemented yet");
	}

	public void debug(final String msg, final Throwable t) {
		logger.debug(msg, t);
	}

	public boolean isInfoEnabled() {
		return isLoggerEnabled(Level.INFO);
	}

	public void info(final String msg) {
		logger.info(msg);
	}

	public void info(final String format, final Object arg) {
		// TODO info format + object
		throw new UnsupportedOperationException("info(String, Object) is not implemented yet");
	}

	public void info(final String format, final Object arg1, final Object arg2) {
		// TODO info format + multiple objects
		throw new UnsupportedOperationException("info(String, Object, Object) is not implemented yet");
	}

	public void info(final String format, final Object[] argArray) {
		// TODO info format + object array
		throw new UnsupportedOperationException("info(String, Object[]) is not implemented yet");
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
		throw new UnsupportedOperationException("warn(String, Object) is not implemented yet");
	}

	public void warn(final String format, final Object arg1, final Object arg2) {
		// TODO warn format + multiple objects
		throw new UnsupportedOperationException("warn(String, Object, Object) is not implemented yet");
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
		throw new UnsupportedOperationException("error(String, Object) is not implemented yet");
	}

	public void error(final String format, final Object arg1, final Object arg2) {
		// TODO error format + multiple objects
		throw new UnsupportedOperationException("error(String, Object, Object) is not implemented yet");
	}

	public void error(final String format, final Object[] argArray) {
		// TODO error format + object array
		throw new UnsupportedOperationException("error(String, Object[]) is not implemented yet");
	}

	public void error(final String msg, final Throwable t) {
		logger.error(msg, t);
	}
	
	private boolean isLoggerEnabled(final Level level) {
		return logger.getEffectiveLevel().toInt() <= level.toInt();
	}
	
}
