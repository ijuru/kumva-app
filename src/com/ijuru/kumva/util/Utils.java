package com.ijuru.kumva.util;

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
}
