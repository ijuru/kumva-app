package com.ijuru.kumva.activity;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class EntryActivity extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		
		Definition definition = ((KumvaApplication)getApplication()).getDefinition();
		 
		setItemTextOrHide(R.id.prefix, definition.getPrefix(), false);
		setItemTextOrHide(R.id.lemma, definition.getLemma(), false);
		
		TextView modifier = (TextView)findViewById(R.id.modifier);
		TextView pronunciation = (TextView)findViewById(R.id.pronunciation);
		TextView wordclass = (TextView)findViewById(R.id.wordclass);
		
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
			
		setItemTextOrHide(R.id.meaning, Utils.formatMeanings(definition.getMeanings()), true);
		setItemTextOrHide(R.id.comment, definition.getComment(), true);	
		setItemTextOrHide(R.id.examples, Utils.formatExamples(definition.getExamples()), true);
	}
	
	/**
	 * Sets the text of a text view or hides it if the text is empty
	 * @param itemId the text view identifier
	 * @param text the text
	 * @param parseRefs true if references in the text should be parsed
	 */
	private void setItemTextOrHide(int itemId, CharSequence text, boolean parseRefs) {
		TextView view = (TextView)findViewById(itemId);
		view.setMovementMethod(LinkMovementMethod.getInstance());
		
		if (!Utils.isEmpty(text)) {
			if (parseRefs && text instanceof String)
				text = parseReferences((String)text);
			view.setText(text);
		}
		else
			view.setVisibility(View.GONE);
	}
	
	/**
	 * Parses references into clickable spans
	 * @param text
	 * @return
	 */
	private Spannable parseReferences(String text) {	
		String clean = text.replace("{", "").replace("}", "");
		Spannable spanner = Spannable.Factory.getInstance().newSpannable(clean);
		
		int start = 0;
		for (int c = 0, cleanPos = 0; c < text.length(); c++) {
			if (text.charAt(c) == '{') {
				start = cleanPos;
			}
			else if (text.charAt(c) == '}') {
				final int end = cleanPos;
				final String ref = clean.substring(start, end);
				spanner.setSpan(new ClickableSpan() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
						intent.putExtra("query", ref);
						startActivity(intent);

					}}, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
			else
				++cleanPos;
		}
		
		return spanner;
	}
}