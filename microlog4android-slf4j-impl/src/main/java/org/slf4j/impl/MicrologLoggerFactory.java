package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.code.microlog4android.LoggerFactory;

/**
 * 
 * @author Jarle Hansen (hansjar@gmail.com)
 *
 */
public class MicrologLoggerFactory implements ILoggerFactory {
	
	public MicrologLoggerFactory() {
	}
	
	public Logger getLogger(final String name) {
		return new MicrologLoggerAdapter(LoggerFactory.getLogger(name));
	}
}
