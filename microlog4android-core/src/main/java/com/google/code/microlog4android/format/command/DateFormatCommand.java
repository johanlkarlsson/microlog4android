/*
 * Copyright 2009 The MicroLog project @sourceforge.net
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
package com.google.code.microlog4android.format.command;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

import com.google.code.microlog4android.Level;


/**
 * This class is used for formatting dates.
 * 
 * Minimum requirements; CLDC 1.0
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * 
 */
public class DateFormatCommand implements FormatCommandInterface {
	private static final String TAG = "Microlog.DateFormatCommand";
	
	public static int USER_FORMAT = 0;

	public final static int ABSOLUTE_FORMAT = 1;

	public final static int DATE_FORMAT = 2;

	public final static int ISO_8601_FORMAT = 3;

	public final static String ABSOLUTE_FORMAT_STRING = "ABSOLUTE";

	public final static String DATE_FORMAT_STRING = "DATE";

	public final static String ISO_8601_FORMAT_STRING = "ISO8601";

	final static String[] MONTH_ARRAY = { "JAN", "FEB", "MAR", "APR",
			"MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	private final Calendar calendar = Calendar.getInstance();

	int format = ABSOLUTE_FORMAT;

	/**
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#execute(String,
	 *      java.lang.String, long, com.google.code.microlog4android.Level, java.lang.Object, java.lang.Throwable)
	 */
	public String execute(String clientID, String name, long time, Level level,
			Object message, Throwable throwable) {

		String dateString = "";
		long currentTime = System.currentTimeMillis();

		switch (format) {
		case ABSOLUTE_FORMAT:
			dateString = toAbsoluteFormat(currentTime);
			break;

		case DATE_FORMAT:
			dateString = toDateFormat(currentTime);
			break;

		case ISO_8601_FORMAT:
			dateString = toISO8601Format(currentTime);
			break;

		default:
			Log.e(TAG, "Unrecognized format, using default format.");
			dateString = toAbsoluteFormat(System.currentTimeMillis());
			break;
		}

		return dateString;
	}

	/**
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#init(java.lang.String)
	 */
	public void init(String initString) {
		if (initString.equals(ABSOLUTE_FORMAT_STRING)) {
			format = ABSOLUTE_FORMAT;
		} else if (initString.equals(DATE_FORMAT_STRING)) {
			format = DATE_FORMAT;
		} else if (initString.equals(ISO_8601_FORMAT_STRING)) {
			format = ISO_8601_FORMAT;
		}
	}

	/**
	 * Format as an absolute date time format, that is
	 * 
	 * @param time
	 *            the time to format.
	 * @return the formatted <code>String</code>.
	 */
	String toAbsoluteFormat(long time) {

		calendar.setTime(new Date(time));
		long hours = calendar.get(Calendar.HOUR_OF_DAY);
		StringBuffer buffer = new StringBuffer();

		if (hours < 10) {
			buffer.append('0');
		}
		buffer.append(hours);

		buffer.append(':');

		long minutes = calendar.get(Calendar.MINUTE);
		if (minutes < 10) {
			buffer.append('0');
		}
		buffer.append(minutes);

		buffer.append(':');

		long seconds = calendar.get(Calendar.SECOND);
		if (seconds < 10) {
			buffer.append('0');
		}
		buffer.append(seconds);

		buffer.append(',');

		long milliseconds = calendar.get(Calendar.MILLISECOND);
		if (milliseconds < 10) {
			buffer.append('0');
		}
		buffer.append(milliseconds);

		return buffer.toString();
	}

	String toDateFormat(long time) {

		calendar.setTime(new Date(time));
		StringBuffer buffer = new StringBuffer();

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			buffer.append('0');
		}
		buffer.append(day);
		buffer.append(' ');

		int month = calendar.get(Calendar.MONTH);
		buffer.append(MONTH_ARRAY[month]);
		buffer.append(' ');

		int year = calendar.get(Calendar.YEAR);
		buffer.append(year);
		buffer.append(' ');

		String absoluteTimeString = toAbsoluteFormat(time);
		buffer.append(absoluteTimeString);

		return buffer.toString();
	}

	String toISO8601Format(long time) {

		calendar.setTime(new Date(time));
		StringBuffer buffer = new StringBuffer();

		int year = calendar.get(Calendar.YEAR);
		buffer.append(year);
		buffer.append('-');

		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			buffer.append('0');
		}
		buffer.append(month);
		buffer.append('-');

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			buffer.append('0');
		}
		buffer.append(day);
		buffer.append(' ');

		String absoluteTimeString = toAbsoluteFormat(time);
		buffer.append(absoluteTimeString);

		return buffer.toString();
	}

}
