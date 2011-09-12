package com.ijuru.kumva.activity;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.R;
import com.ijuru.kumva.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EntryActivity extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry);
		
		Definition definition = ((KumvaApplication)getApplication()).getDefinition();
		 
		TextView prefix = (TextView)findViewById(R.id.prefix);
		TextView lemma = (TextView) findViewById(R.id.lemma);
		TextView modifier = (TextView)findViewById(R.id.modifier);
		TextView meaning = (TextView)findViewById(R.id.meaning);
		TextView pronunciation = (TextView)findViewById(R.id.pronunciation);
		TextView wordclass = (TextView)findViewById(R.id.wordclass);

		prefix.setText(definition.getPrefix());
		lemma.setText(definition.getLemma());
		
		if (!Utils.isEmpty(definition.getModifier()))
			modifier.setText("(" + definition.getModifier() + ")");
		
		if (!Utils.isEmpty(definition.getPronunciation()))
			pronunciation.setText("/" + definition.getPronunciation() + "/");
		else
			pronunciation.setVisibility(View.GONE);
		
		if (!Utils.isEmpty(definition.getWordClass())) {
			String strIdName = "wcls_" + definition.getWordClass();
			int strId = getResources().getIdentifier(strIdName, "string", "com.ijuru.kumva");
			wordclass.setText(getString(strId));
		}
		else
			wordclass.setVisibility(View.GONE);
			
		meaning.setText(Utils.formatMeaning(definition));
	}
	
}
