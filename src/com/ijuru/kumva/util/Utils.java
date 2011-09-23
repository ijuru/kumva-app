package com.ijuru.kumva.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ijuru.kumva.Example;
import com.ijuru.kumva.Meaning;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

/**
 * Utility methods
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
			Log.e("Kumva", e.getMessage());
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
	 * Converts a ISO-639 languuage code to a language name
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
	 * Parses a CSV string into an array of integers
	 * @param csv the string
	 * @return the array of integers
	 */
	public static int[] parseCSVInts(String csv) {
		String[] vals = csv.split(",");
		List<Integer> ints = new ArrayList<Integer>();
		for (String val : vals) {
			String v = val.trim();
			if (v.length() > 0)
				ints.add(Integer.parseInt(val));
		}
		return integerListToArray(ints);
	}
	
	/**
	 * Converts a list of integer objects to a primitive array of ints
	 * @param list the list
	 * @return the primitive array
	 */
	public static int[] integerListToArray(List<Integer> list) {
		int[] ints = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			ints[i] = list.get(i);
		return ints;
	}
}
