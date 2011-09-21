package com.ijuru.kumva.activity;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DictionaryPrefActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	private boolean isNew = false;
	private Dictionary dictionary;
	private EditTextPreference name;
	private EditTextPreference url;
	
	/**
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_dictionary);
        
        this.name = (EditTextPreference)getPreferenceScreen().findPreference("dict_name");
		this.url = (EditTextPreference)getPreferenceScreen().findPreference("dict_url");
        
		// Get existing dictionary if specified
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	String dictName = extras.getString("dict");
        	if (dictName != null)
        		this.dictionary = ((KumvaApplication)getApplication()).getDictionaryByName(dictName);
        }
        
        // Or create new dictionary
        if (this.dictionary == null) {
        	this.dictionary = new Dictionary("", "http://", "", "");
        	this.isNew = true;
        	this.setTitle(getString(R.string.str_adddictionary));
        }
        
        name.setText(dictionary.getName());
		name.setSummary(dictionary.getName());
		url.setText(dictionary.getUrl());
		url.setSummary(dictionary.getUrl());
        
        this.name.setOnPreferenceChangeListener(this);
        this.url.setOnPreferenceChangeListener(this);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dictionarypref, menu);
	    return true;
	}

	/**
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Disable delete option if this is an unsaved dictionary
		menu.getItem(0).setEnabled(!isNew);
		return true;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menudictdelete:
			onMenuDelete();
			break;
		case R.id.menudictsave:
			onMenuSave();
			break;
		case R.id.menudictdiscard:
			finish();
			break;
		}
		return true;
	}
	
	/**
	 * Called when user selects Delete from options menu
	 */
	private void onMenuDelete() {	
		KumvaApplication app = (KumvaApplication)getApplication();
		app.deleteDictionary(dictionary);
		
		finish();
	}
	
	/**
	 * Called when user selects Save from options menu
	 */
	private void onMenuSave() {
		this.dictionary.setName(this.name.getText());
		this.dictionary.setUrl(this.url.getText());
		
		if (isNew) {
			KumvaApplication app = (KumvaApplication)getApplication();
			app.addDictionary(dictionary);
		}	
		
		finish();
	}

	/**
	 * Called when a preference value is about to be changed
	 */
	@Override
	public boolean onPreferenceChange(Preference pref, Object value) {
		if (pref instanceof EditTextPreference) {
			// Update summary to be the preference value
			String newVal = (String)value;
			EditTextPreference edit = (EditTextPreference)pref;
			edit.setSummary(newVal);
		}
		return true;
	}
}
