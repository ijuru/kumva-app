package com.ijuru.kumva.ui;

import com.ijuru.kumva.Dictionary;

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
			TextView summary = (TextView)view.findViewById(android.R.id.summary);
			summary.setText(dictionary.getUrl());
		}

		return view;
	}
}
