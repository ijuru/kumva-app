package com.ijuru.kumva.util;

import android.app.AlertDialog;
import android.content.Context;

public class Utils {
	public static void alert(Context context, String message) {
		new AlertDialog.Builder(context).setMessage(message).show();
	}
}
