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

import com.ijuru.kumva.app.site.Dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Special list adapter to display definition objects
 */
public class DictionaryListAdapter extends ArrayAdapter<Dictionary> {
	
	private LayoutInflater inflater;
	
	/**
	 * Constructs this adapter from the given context
	 * 
	 * @param context
	 *            the context
	 */
	public DictionaryListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_single_choice);

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null)
			view = inflater.inflate(android.R.layout.simple_list_item_single_choice, null);
		
		Dictionary dictionary = this.getItem(position);
		if (dictionary != null) {	
			TextView text = (TextView)view.findViewById(android.R.id.text1);
			text.setText(dictionary.getName());
		}

		return view;
	}
}
