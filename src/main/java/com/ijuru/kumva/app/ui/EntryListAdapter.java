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

import com.ijuru.kumva.app.Entry;
import com.ijuru.kumva.app.R;
import com.ijuru.kumva.app.util.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Special list adapter to display definition objects
 */
public class EntryListAdapter extends ArrayAdapter<Entry> {
	
	private LayoutInflater inflater;
	
	/**
	 * Constructs this adapter from the given context
	 * 
	 * @param context
	 *            the context
	 */
	public EntryListAdapter(Context context) {
		super(context, R.layout.list_item_entry);

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
			view = inflater.inflate(R.layout.list_item_entry, null);

		Entry definition = this.getItem(position);
		if (definition != null) {
			TextView prefix = (TextView) view.findViewById(R.id.prefix);
			TextView lemma = (TextView) view.findViewById(R.id.lemma);
			TextView modifier = (TextView) view.findViewById(R.id.modifier);
			TextView meaning = (TextView) view.findViewById(R.id.meaning);

			prefix.setText(definition.getPrefix());
			lemma.setText(definition.getLemma());
			
			if (!Utils.isEmpty(definition.getModifier()))
				modifier.setText("(" + definition.getModifier() + ")");
			else
				modifier.setText("");
			
			meaning.setText(Format.meanings(getContext(), definition.getMeanings(), false));
		}

		return view;
	}
}
