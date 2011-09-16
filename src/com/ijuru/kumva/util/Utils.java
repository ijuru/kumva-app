package com.ijuru.kumva.util;

import java.util.ArrayList;
import java.util.List;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Example;
import com.ijuru.kumva.Meaning;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Utility methods
 */
public class Utils {
	/**
	 * Displays a simple alert dialog
	 * @param context the context
	 * @param message the alert message
	 */
	public static void alert(Context context, String message) {
		new AlertDialog.Builder(context).setMessage(message).show();
	}

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
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param definition the definition
	 * @return the formatted string
	 */
	public static String formatMeanings(Definition definition) {
		if (definition.getMeanings().size() == 1)
			return definition.getMeanings().get(0).getText();

		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (Meaning meaning : definition.getMeanings()) {
			sb.append(index + ". " + meaning.getText() + "\n");
			++index;
		}
		return sb.toString();
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param definition the definition
	 * @return the formatted string
	 */
	public static String formatExamples(Definition definition) {
		StringBuilder sb = new StringBuilder();
		for (Example example : definition.getExamples()) {
			sb.append(example.getUsage() + "\n" + example.getMeaning() + "\n\n");
		}
		return sb.toString();
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
	
	public static int[] integerListToArray(List<Integer> list) {
		int[] ints = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			ints[i] = list.get(i);
		return ints;
	}
}
