/**
 * Copyright 2011 Rowan Seymour
 * 
 * This file is part of Kumva.
 *
 * Kumva is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kumva is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kumva. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ijuru.kumva.app.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * General utility methods
 */
public class Utils {
	/**
	 * Gets the version name from the manifest
	 * @param context the context
	 * @return the version name
	 */
	public static String getVersionName(Context context) {
		try {
			String packageName = context.getPackageName();
			return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Checks if the given string is NULL or empty
	 * @param str the string to check
	 * @return true if string is empty
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * Capitalizes the given string
	 * @param str the string
	 * @return the capitalized string
	 */
	public static String capitalize(String str) {
		if (str.length() == 0)
			return "";
		
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
	/**
	 * Parses a string into an integer
	 * @param val the string
	 * @return the integer or null if not a valid integer
	 */
	public static Integer parseInteger(String val) {
		try {
			return Integer.parseInt(val);
		}
		catch (NumberFormatException ex) {
			return null;
		}
	}
	
	/**
	 * Converts a pixel value to a device-independent pixel value
	 * @param context the context
	 * @param pixels the pixel value
	 * @return the device-independent pixel value
	 */
	public static int pixels(Context context, int pixels) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}
	
	/**
	 * Converts a ISO-639 language code to a language name
	 * @param code the code
	 * @return the name
	 */
	public static String getLanguageName(String code) {
		return new Locale(code).getDisplayLanguage();
	}
	
	/**
	 * Makes a CSV string from a collection of objects
	 * @param vals the objects
	 * @return the CSV string
	 */
	public static String makeCSV(Collection<?> vals) {
		StringBuilder builder = new StringBuilder();
		Iterator<?> iter = vals.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next().toString());
		    if (!iter.hasNext()) {
		       break;                  
		    }
		    builder.append(", ");
		}
		return builder.toString();
	}
	
	/**
	 * Parses a CSV string into a list of strings, removing spaces and empty strings
	 * @param csv the string
	 * @return the list of strings
	 */
	public static List<String> parseCSV(String csv) {
		String[] vals = csv.split(",");
		List<String> strs = new ArrayList<String>();
		for (String val : vals) {
			String v = val.trim();
			if (v.length() > 0)
				strs.add(v);
		}
		return strs;
	}
	
	/**
	 * Parses a CSV string into a list of integers
	 * @param csv the string
	 * @return the list of integers
	 */
	public static List<Integer> parseCSVIntegers(String csv) {
		String[] vals = csv.split(",");
		List<Integer> ints = new ArrayList<Integer>();
		for (String val : vals) {
			String v = val.trim();
			if (v.length() > 0) {
				Integer n = parseInteger(v);
				if (n != null)
					ints.add(n);
			}
		}
		return ints;
	}
}
