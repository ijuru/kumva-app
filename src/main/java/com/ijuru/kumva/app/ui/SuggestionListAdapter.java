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
import com.ijuru.kumva.app.site.Suggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Special list adapter to display suggestion objects
 */
public class SuggestionListAdapter extends ArrayAdapter<Suggestion> {
	
	private LayoutInflater inflater;
	
	/**
	 * Constructs this adapter from the given context
	 * @param context the context
	 */
	public SuggestionListAdapter(Context context) {
		super(context, R.layout.list_item_suggestion);

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.list_item_suggestion, null);

		Suggestion suggestion = this.getItem(position);
		if (suggestion != null) {
			TextView text = (TextView) view.findViewById(R.id.suggestion);
			text.setText(suggestion.getText());
		}

		return view;
	}
}
