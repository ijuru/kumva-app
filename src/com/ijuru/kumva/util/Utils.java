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

package com.ijuru.kumva.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.ijuru.kumva.Example;
import com.ijuru.kumva.Meaning;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Html;
import android.text.Spanned;

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
	 * Converts a ISO-639 language code to a language name
	 * @param code the code
	 * @return the name
	 */
	public static String getLanguageName(String code) {
		return new Locale(code).getDisplayLanguage();
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param meanings the meanings
	 * @return the formatted string
	 */
	public static String formatMeanings(List<Meaning> meanings) {
		if (meanings.size() == 1)
			return meanings.get(0).getText();

		StringBuilder sb = new StringBuilder();
		for (int m = 0; m < meanings.size(); ++m) {
			Meaning meaning = meanings.get(m);
			sb.append((m + 1) + ". " + meaning.getText());
			if (m < meanings.size() - 1)
				sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param examples the examples
	 * @return the formatted string
	 */
	public static Spanned formatExamples(List<Example> examples) {
		StringBuilder sb = new StringBuilder();
		for (int e = 0; e < examples.size(); ++e) {
			Example example = examples.get(e);
			sb.append(example.getUsage() + "<br /><i>" + example.getMeaning() + "</i>");
			if (e < examples.size() - 1)
				sb.append("<br /><br />");
		}
		return Html.fromHtml(sb.toString());
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
			if (v.length() > 0)
				ints.add(Integer.parseInt(val));
		}
		return ints;
	}
	
	public static String makeCSV(Collection<?> vals) {
	     StringBuilder builder = new StringBuilder();
	     Iterator<?> iter = vals.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(", ");
	     }
	     return builder.toString();
	 }
}
