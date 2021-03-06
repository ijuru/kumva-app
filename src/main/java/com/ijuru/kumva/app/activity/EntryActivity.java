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

import com.ijuru.kumva.Entry;
import com.ijuru.kumva.Revision;
import com.ijuru.kumva.app.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.app.ui.AudioButton;
import com.ijuru.kumva.app.ui.Format;
import com.ijuru.kumva.app.util.Utils;
import com.ijuru.kumva.remote.RemoteDictionary;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Activity to show details of a dictionary entry
 */
public class EntryActivity extends ActionBarActivity {

	/**
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Set section titles
		((TextView)findViewById(R.id.headerPronunciation)).setText(R.string.str_pronunciation);
		((TextView)findViewById(R.id.headerMeaning)).setText(R.string.str_meaning);
		((TextView)findViewById(R.id.headerDerivation)).setText(R.string.str_derivation);
		((TextView)findViewById(R.id.headerExamples)).setText(R.string.str_examples);
		
		KumvaApplication app = (KumvaApplication) getApplication();
		Entry entry = app.getCurrentEntry();
		RemoteDictionary dictionary = app.getActiveDictionary();

		displayEntry(dictionary, entry);
	}

	/**
	 * @see android.support.v7.app.ActionBarActivity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.entry, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @see android.support.v7.app.ActionBarActivity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Setup display for the given entry
	 * @param dictionary the dictionary
	 * @param entry the entry
	 */
	protected void displayEntry(RemoteDictionary dictionary, Entry entry) {
		getSupportActionBar().setTitle(formatTitle(entry));

		Revision definition = entry.getRevisions().get(0);

		setItemTextOrHide(R.id.prefix, definition.getPrefix());
		setItemTextOrHide(R.id.lemma, definition.getLemma());

		TextView modifier = (TextView)findViewById(R.id.modifier);
		TextView pronunciation = (TextView)findViewById(R.id.pronunciation);
		AudioButton audioBtn = (AudioButton)findViewById(R.id.audiobtn);
		TextView wordclass = (TextView)findViewById(R.id.wordclass);

		// Display modifier in brackets
		if (!TextUtils.isEmpty(definition.getModifier())) {
			modifier.setText("(" + definition.getModifier() + ")");
		}

		// Display pronunciation/amasaku
		if (!TextUtils.isEmpty(definition.getPronunciation())) {
			pronunciation.setText("/" + definition.getPronunciation() + "/");
		}
		else {
			pronunciation.setVisibility(View.GONE);
		}

		// Display audio button if there is a URL
		if (!TextUtils.isEmpty(entry.getAudioUrl())) {
			audioBtn.setMediaPlayer(((KumvaApplication) getApplication()).getMediaPlayer());
			audioBtn.setAudioURL(entry.getAudioUrl());
		}
		else {
			audioBtn.setVisibility(View.GONE);
		}

		// Display word class and noun classes
		if (!TextUtils.isEmpty(definition.getWordClass())) {
			String strIdName = "wcls_" + definition.getWordClass();
			int strId = getResources().getIdentifier(strIdName, "string", getApplication().getPackageName());
			StringBuilder sb = new StringBuilder(getString(strId));

			// Create noun classes string
			if (definition.getNounClasses().size() > 0) {
				sb.append(" (");
				sb.append(getString(definition.getNounClasses().size() > 1 ? R.string.str_classes : R.string.str_class).toLowerCase());
				sb.append(" ");
				sb.append(Utils.makeCSV(definition.getNounClasses()));
				sb.append(")");
			}

			wordclass.setText(sb.toString());
		}
		else {
			wordclass.setVisibility(View.GONE);
		}

		setItemTextOrHide(R.id.meaning, Format.meanings(this, definition.getMeanings(), true));
		setItemTextOrHide(R.id.comment, Format.parseQueryLinks(this, definition.getComment()));
		setItemTextOrHide(R.id.derivation, Format.rootList(this, dictionary.getDefinitionLang(), definition.getTags("root")));
		setItemTextOrHide(R.id.examples, Format.examples(definition.getExamples()));
	}

	/**
	 * Sets the text of a text view or hides it if the text is empty
	 * @param itemId the text view identifier
	 * @param text the text
	 */
	protected void setItemTextOrHide(int itemId, CharSequence text) {
		TextView view = (TextView)findViewById(itemId);
		view.setMovementMethod(LinkMovementMethod.getInstance());
		
		if (!TextUtils.isEmpty(text)) {
			view.setText(text);
		}
		else {
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * Formats the actionbar title representation of the entry
	 * @param entry the entry
	 * @return the formatted text
	 */
	protected String formatTitle(Entry entry) {
		Revision definition = entry.getRevisions().get(0);

		String prefix = TextUtils.isEmpty(definition.getPrefix()) ? "" : definition.getPrefix();
		String lemma = TextUtils.isEmpty(definition.getLemma()) ? "" : definition.getLemma();
		return prefix + lemma;
	}
}
