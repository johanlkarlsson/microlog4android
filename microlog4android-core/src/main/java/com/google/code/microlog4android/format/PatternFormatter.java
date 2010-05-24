/*
 * Copyright 2008 The Microlog project @sourceforge.net
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.microlog4android.format;

import java.util.Vector;

import android.util.Log;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.command.CategoryFormatCommand;
import com.google.code.microlog4android.format.command.ClientIdFormatCommand;
import com.google.code.microlog4android.format.command.DateFormatCommand;
import com.google.code.microlog4android.format.command.FormatCommandInterface;
import com.google.code.microlog4android.format.command.MessageFormatCommand;
import com.google.code.microlog4android.format.command.NoFormatCommand;
import com.google.code.microlog4android.format.command.PriorityFormatCommand;
import com.google.code.microlog4android.format.command.ThreadFormatCommand;
import com.google.code.microlog4android.format.command.ThrowableFormatCommand;
import com.google.code.microlog4android.format.command.TimeFormatCommand;


/**
 * This class is the equivalent to the <code>PatternLayout</code> found in
 * Log4j. As far as possible all conversions should be available.
 * 
 * <pre>
 *  Q: How do I setup the PatternFormatter in my microlog.properties file?
 * 
 *  A: You use the microlog.formatter.PatternFormatter.pattern property to set the pattern for the PatternFormatter.
 * 
 *  Example:
 *  # Set the pattern for the PatternFormatter
 *  microlog.formatter.PatternFormatter.pattern=%r %c %m %T
 * 
 *  This would print the relative logging time, the name of the Logger, the logged message and the Throwable object (if not null).
 * 
 *  The available pattern conversions are:
 *  %i : the client id
 *  %c : prints the name of the Logger
 *  %d : prints the date (absolute time)
 *  %m : prints the logged message
 *  %P : prints the priority, i.e. Level of the message.
 *  %r : prints the relative time of the logging. (The first logging is done at time 0.)
 *  %t : prints the thread name.
 *  %T : prints the Throwable object.
 *  %% : prints the '%' sign.
 * </pre>
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * @since 0.6
 */
public class PatternFormatter implements Formatter {
	private static final String TAG = "Microlog.PatternFormatter";
	
	public static final String PATTERN_PROPERTY = "pattern";

	/**
	 * This is the default pattern that is used for the
	 * <code>PatternFormatter</code>.
	 * 
	 * Developer notice: If you change the pattern, please test that the pattern
	 * works as expected. Otherwise it is possible that the default (no
	 * argument) constructor do not work as expected.
	 */
	public static final String DEFAULT_CONVERSION_PATTERN = "%r %c{1} [%P] %m %T";

	public static final char CLIENT_ID_CONVERSION_CHAR = 'i';
	public static final char CATEGORY_CONVERSION_CHAR = 'c';
	public static final char DATE_CONVERSION_CHAR = 'd';
	public static final char MESSAGE_CONVERSION_CHAR = 'm';
	public static final char PRIORITY_CONVERSION_CHAR = 'P';
	public static final char RELATIVE_TIME_CONVERSION_CHAR = 'r';
	public static final char THREAD_CONVERSION_CHAR = 't';
	public static final char THROWABLE_CONVERSION_CHAR = 'T';
	public static final char PERCENT_CONVERSION_CHAR = '%';

	private static final String[] PROPERTY_NAMES = { PatternFormatter.PATTERN_PROPERTY };

	private String pattern = DEFAULT_CONVERSION_PATTERN;
	private FormatCommandInterface[] commandArray;

	private boolean patternParsed;

	/**
	 * Create a <code>PatternFormatter</code> with the default pattern.
	 */
	public PatternFormatter() {
		patternParsed = false;
	}

	/**
	 * Format the input parameters.
	 * 
	 * @see com.google.code.microlog4android.format.Formatter#format(String, String, long,
	 *      com.google.code.microlog4android.Level, java.lang.Object, java.lang.Throwable)
	 */
	public String format(String clientID, String name, long time, Level level,
			Object message, Throwable t) {

		if (!patternParsed && pattern != null) {
			parsePattern(pattern);
		}

		StringBuffer formattedStringBuffer = new StringBuffer(64);
		if (commandArray != null) {
			int length = commandArray.length;

			for (int index = 0; index < length; index++) {
				FormatCommandInterface currentConverter = commandArray[index];
				if (currentConverter != null) {
					formattedStringBuffer.append(currentConverter.execute(
							clientID, name, time, level, message, t));
				}
			}
		}

		return formattedStringBuffer.toString();
	}

	/**
	 * Get the pattern that is when formatting.
	 * 
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Set the pattern that is when formatting.
	 * 
	 * @param pattern
	 *            the pattern to set
	 * @throws IllegalArgumentException
	 *             if the pattern is null.
	 */
	public void setPattern(String pattern) throws IllegalArgumentException {
		if (pattern == null) {
			throw new IllegalArgumentException("The pattern must not be null.");
		}

		this.pattern = pattern;
		parsePattern(this.pattern);
	}

	/**
	 * Parse the pattern.
	 * 
	 * This creates a command array that is executed when formatting the log
	 * message.
	 */
	private void parsePattern(String pattern) {

		int currentIndex = 0;
		int patternLength = pattern.length();
		Vector<FormatCommandInterface> converterVector = new Vector<FormatCommandInterface>(20);

		while (currentIndex < patternLength) {
			char currentChar = pattern.charAt(currentIndex);

			if (currentChar == '%') {

				currentIndex++;
				currentChar = pattern.charAt(currentIndex);

				switch (currentChar) {
				case CLIENT_ID_CONVERSION_CHAR:
					converterVector.addElement(new ClientIdFormatCommand());
					break;
				case CATEGORY_CONVERSION_CHAR:
					CategoryFormatCommand categoryFormatCommand = new CategoryFormatCommand();
					String specifier = extraxtSpecifier(pattern, currentIndex);
					int specifierLength = specifier.length();
					if (specifierLength > 0) {
						categoryFormatCommand.init(specifier);
						currentIndex = currentIndex + specifierLength + 2;
					}
					converterVector.addElement(categoryFormatCommand);
					break;
				case DATE_CONVERSION_CHAR:
					DateFormatCommand formatCommand = new DateFormatCommand();
					specifier = extraxtSpecifier(pattern, currentIndex);
					specifierLength = specifier.length();
					if (specifierLength > 0) {
						formatCommand.init(specifier);
						currentIndex = currentIndex + specifierLength + 2;
					}
					converterVector.addElement(formatCommand);
					break;
				case MESSAGE_CONVERSION_CHAR:
					converterVector.addElement(new MessageFormatCommand());
					break;

				case PRIORITY_CONVERSION_CHAR:
					converterVector.addElement(new PriorityFormatCommand());
					break;

				case RELATIVE_TIME_CONVERSION_CHAR:
					converterVector.addElement(new TimeFormatCommand());
					break;

				case THREAD_CONVERSION_CHAR:
					converterVector.addElement(new ThreadFormatCommand());
					break;

				case THROWABLE_CONVERSION_CHAR:
					converterVector.addElement(new ThrowableFormatCommand());
					break;

				case PERCENT_CONVERSION_CHAR:
					NoFormatCommand noFormatCommand = new NoFormatCommand();
					noFormatCommand.init("%");
					converterVector.addElement(noFormatCommand);
					break;

				default:
					Log.e(TAG, "Unrecognized conversion character "
							+ currentChar);
					break;
				}

				currentIndex++;

			} else {

				int percentIndex = pattern.indexOf("%", currentIndex);
				String noFormatString = "";
				if (percentIndex != -1) {
					noFormatString = pattern.substring(currentIndex,
							percentIndex);
				} else {
					noFormatString = pattern.substring(currentIndex,
							patternLength);
				}

				NoFormatCommand noFormatCommand = new NoFormatCommand();
				noFormatCommand.init(noFormatString);
				converterVector.addElement(noFormatCommand);

				currentIndex = currentIndex + noFormatString.length();
			}

		}

		commandArray = new FormatCommandInterface[converterVector.size()];
		converterVector.copyInto(commandArray);

		patternParsed = true;
	}

	String extraxtSpecifier(String pattern, int index) {
		String specifier = "";

		int beginIndex = pattern.indexOf('{', index);
		int endIndex = pattern.indexOf('}', index);

		if (beginIndex > 0 && endIndex > beginIndex) {
			specifier = pattern.substring(beginIndex + 1, endIndex);
		}

		return specifier;
	}

	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	/**
	 * @see com.google.code.microlog4android.format.Formatter#setProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public void setProperty(String name, String value) {

		if (name.equals(PatternFormatter.PATTERN_PROPERTY)) {
			this.setPattern(value);
		}

	}

}
