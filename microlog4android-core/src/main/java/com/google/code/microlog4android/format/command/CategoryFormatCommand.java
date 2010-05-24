/*
 * Copyright 2008 The MicroLog project @sourceforge.net
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

import android.util.Log;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.format.command.util.StringUtil;

/**
 * The <code>CategoryFormatCommand</code> is used for printing the category,
 * i.e. the name of the logging class.
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 * 
 * @since 1.0
 */
public class CategoryFormatCommand implements FormatCommandInterface {
	private static final String TAG = "Microlog.CategoryFormatCommand";
	
	public static final int FULL_CLASS_NAME_SPECIFIER = -1;

	public static final int DEFAULT_PRECISION_SPECIFIER = 1;

	private int precisionSpecifier = DEFAULT_PRECISION_SPECIFIER;

	/**
	 * 
	 * 
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#execute(String,
	 *      String, long, com.google.code.microlog4android.Level, java.lang.Object,
	 *      java.lang.Throwable)
	 */
	public String execute(String clientID, String name, long time, Level level,
			Object message, Throwable throwable) {
		String convertedData = "";

		if (name != null) {
			if (precisionSpecifier == FULL_CLASS_NAME_SPECIFIER) {
				convertedData = name;
			} else {
				convertedData = StringUtil.extractPartialClassName(name,
						precisionSpecifier);
			}
		}

		return convertedData;
	}

	/**
	 * 
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#init(java.lang.String)
	 */
	public void init(String initString) {
		try {
			precisionSpecifier = Integer.parseInt(initString);
			System.out.println("Precision specifier for %c is "
					+ precisionSpecifier);
		} catch (NumberFormatException e) {
			Log.e(TAG, "Failed to parse the specifier for the %c pattern "
					+ e);
		}
	}

	/**
	 * Get the precision specifier.
	 * 
	 * @return the precision specifier
	 */
	public int getPrecisionSpecifier() {
		return precisionSpecifier;
	}

	/**
	 * Set the precision specifier.
	 * 
	 * @param precisionSpecifier
	 *            the precision specifier to use.
	 */
	public void setPrecisionSpecifier(int precisionSpecifier) {
		this.precisionSpecifier = precisionSpecifier;
	}

}
