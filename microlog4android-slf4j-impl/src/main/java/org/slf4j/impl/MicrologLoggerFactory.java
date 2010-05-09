package org.slf4j.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.code.microlog4android.Level;

/**
 * 
 * @author Jarle Hansen (hansjar@gmail.com)
 *
 */
public class MicrologLoggerFactory implements ILoggerFactory {
	private Map<String, MicrologLoggerAdapter> loggerAdapters = new HashMap<String, MicrologLoggerAdapter>();
	
	public MicrologLoggerFactory() {
	}
	
	public synchronized Logger getLogger(final String name) {
		Logger logger = loggerAdapters.get(name);
		
		if(logger == null) {
			com.google.code.microlog4android.Logger micrologLogger = new com.google.code.microlog4android.Logger(name);
			micrologLogger.setLevel(Level.DEBUG);
			
			logger = new MicrologLoggerAdapter(micrologLogger);
		}
		
		
		
		return logger;
	}
}
