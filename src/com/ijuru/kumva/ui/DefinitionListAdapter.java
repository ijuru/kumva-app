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

/**
 * Special list adapter to display definition objects
 */
public class DefinitionListAdapter extends ArrayAdapter<Definition> {
	
	private LayoutInflater inflater;
	
	/**
	 * Constructs this adapter from the given context
	 * 
	 * @param context
	 *            the context
	 */
	public DefinitionListAdapter(Context context) {
		super(context, R.layout.definition);

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.definition, null);
		}

		Definition definition = this.getItem(position);
		if (definition != null) {
			TextView prefix = (TextView) view.findViewById(R.id.prefix);
			TextView lemma = (TextView) view.findViewById(R.id.lemma);
			TextView modifier = (TextView) view.findViewById(R.id.modifier);
			TextView meaning = (TextView) view.findViewById(R.id.meaning);

			prefix.setText(definition.getPrefix());
			lemma.setText(definition.getLemma());
			modifier.setText(definition.getModifier());
			meaning.setText(formatMeaning(definition));
		}

		return view;
	}

	/**
	 * Formats a definition's meanings into a single string
	 * 
	 * @param definition
	 *            the definition
	 * @return the formatted string
	 */
	private String formatMeaning(Definition definition) {
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
}
