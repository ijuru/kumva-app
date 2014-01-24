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

package com.ijuru.kumva.app.activity;

import com.ijuru.kumva.app.Entry;
import com.ijuru.kumva.app.KumvaApplication;
import com.ijuru.kumva.app.R;
import com.ijuru.kumva.app.site.Dictionary;
import com.ijuru.kumva.app.ui.AudioButton;
import com.ijuru.kumva.app.ui.Format;
import com.ijuru.kumva.app.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

/**
 * Activity to show details of a dictionary entry
 */
public class EntryActivity extends Activity {
	
	private Entry entry;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		
		KumvaApplication app = (KumvaApplication)getApplication();
		
		Dictionary dictionary = app.getActiveDictionary();
		
		((TextView)findViewById(R.id.headerPronunciation)).setText(R.string.str_pronunciation);
		((TextView)findViewById(R.id.headerMeaning)).setText(R.string.str_meaning);
		((TextView)findViewById(R.id.headerDerivation)).setText(R.string.str_derivation);
		((TextView)findViewById(R.id.headerExamples)).setText(R.string.str_examples);
		
		this.entry = app.getCurrentEntry();

		setItemTextOrHide(R.id.prefix, entry.getPrefix());
		setItemTextOrHide(R.id.lemma, entry.getLemma());
		
		TextView modifier = (TextView)findViewById(R.id.modifier);
		TextView pronunciation = (TextView)findViewById(R.id.pronunciation);
		AudioButton audioBtn = (AudioButton)findViewById(R.id.audiobtn);
		TextView wordclass = (TextView)findViewById(R.id.wordclass);
		
		// Display modifier in brackets
		if (!Utils.isEmpty(entry.getModifier()))
			modifier.setText("(" + entry.getModifier() + ")");
		
		// Display pronunciation/amasaku
		if (!Utils.isEmpty(entry.getPronunciation()))
			pronunciation.setText("/" + entry.getPronunciation() + "/");
		else
			pronunciation.setVisibility(View.GONE);
		
		// Display audio button if there is a URL
		if (!Utils.isEmpty(entry.getAudioURL())) {
			audioBtn.setMediaPlayer(app.getMediaPlayer());
			audioBtn.setAudioURL(entry.getAudioURL());
		}
		else
			audioBtn.setVisibility(View.GONE);
		
		// Display word class and noun classes
		if (!Utils.isEmpty(entry.getWordClass())) {
			String strIdName = "wcls_" + entry.getWordClass();
			int strId = getResources().getIdentifier(strIdName, "string", "com.ijuru.kumva.app");
			StringBuilder sb = new StringBuilder(getString(strId));
			
			// Create noun classes string
			if (entry.getNounClasses().size() > 0) {
				sb.append(" (");
				sb.append(getString(entry.getNounClasses().size() > 1 ? R.string.str_classes : R.string.str_class).toLowerCase());
				sb.append(" ");
				sb.append(Utils.makeCSV(entry.getNounClasses()));
				sb.append(")");
			}
			
			wordclass.setText(sb.toString());
		}
		else
			wordclass.setVisibility(View.GONE);
		
		setItemTextOrHide(R.id.meaning, Format.meanings(this, entry.getMeanings(), true));
		setItemTextOrHide(R.id.comment, Format.parseQueryLinks(this, entry.getComment()));	
		setItemTextOrHide(R.id.derivation, Format.rootList(this, dictionary.getDefinitionLang(), entry.getTags("root")));
		setItemTextOrHide(R.id.examples, Format.examples(entry.getExamples()));
	}

	/**
	 * Sets the text of a text view or hides it if the text is empty
	 * @param itemId the text view identifier
	 * @param text the text
	 */
	private void setItemTextOrHide(int itemId, CharSequence text) {
		TextView view = (TextView)findViewById(itemId);
		view.setMovementMethod(LinkMovementMethod.getInstance());
		
		if (!Utils.isEmpty(text))
			view.setText(text);
		else
			view.setVisibility(View.GONE);
	}
}
