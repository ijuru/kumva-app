package com.ijuru.kumva.ui;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Meaning;
import com.ijuru.kumva.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DefinitionListAdapter extends ArrayAdapter<Definition> {

	public DefinitionListAdapter(Context context) {
		super(context, R.layout.definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v != null)
			return v;

		LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.definition, null);

		Definition definition = this.getItem(position);
		if (definition != null) {
			TextView entry = (TextView)v.findViewById(R.id.entry);
			TextView meaning = (TextView)v.findViewById(R.id.meaning);
		
			entry.setText(definition.getPrefix() + definition.getLemma());
			meaning.setText(formatMeaning(definition));
		}

		return v;
	}
	
	private String formatMeaning(Definition definition) {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (Meaning meaning : definition.getMeanings()) {
			sb.append(index + ". " + meaning.getText() + "\n");
			++index;
		}
		return sb.toString();
	}
}
