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

package com.ijuru.kumva.app.ui;

import com.ijuru.kumva.app.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Utility class for dialogs
 */
public class Dialogs {
	/**
	 * Listener for input from prompt dialogs
	 */
	public interface InputListener {
		public void entered(String text);
	}
	
	/**
	 * Displays a temporary toast message
	 * @param context the context
	 * @param message the toast message
	 */
	public static void toast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Displays a simple error dialog
	 * @param context the context
	 * @param message the alert message
	 */
	public static void error(Context context, String message) {
		alert(context, context.getString(R.string.str_error), message);
	}
	
	/**
	 * Displays a simple alert dialog
	 * @param context the context
	 * @param title the title
	 * @param message the alert message
	 */
	public static void alert(Context context, String title, String message) {
		new AlertDialog.Builder(context)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, null)
			.show();
	}
	
	/**
	 * Displays a text input dialog
	 * @param context the context
	 * @param title the title
	 * @param hint the input hint
	 * @param inputType the input type
	 */
	public static void prompt(Context context, String title, String hint, int inputType, final InputListener listener) {
		final EditText input = new EditText(context);
		input.setHint(hint);
		input.setInputType(inputType);
		
		// Just get some proper padding around the text box
		LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(5, 0, 5, 0);
        layout.addView(input);
        
		new AlertDialog.Builder(context)
			.setTitle(title)
			.setView(layout)
			.setNegativeButton(android.R.string.cancel, null)
		    .setPositiveButton(android.R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					listener.entered(input.getText().toString());
				}
			})
		    .show();
	}
}
