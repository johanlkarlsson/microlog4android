package com.google.code.microlog4android.factory;

import com.google.code.microlog4android.repository.DefaultLoggerRepository;
import com.google.code.microlog4android.repository.LoggerRepository;

public enum DefaultRepositoryFactory {
	;
	
	public static LoggerRepository getDefaultLoggerRepository() {
		return DefaultLoggerRepository.INSTANCE;
	}
}
