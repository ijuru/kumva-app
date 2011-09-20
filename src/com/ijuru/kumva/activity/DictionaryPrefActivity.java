package com.ijuru.kumva.activity;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DictionaryPrefActivity extends PreferenceActivity implements OnPreferenceChangeListener {

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
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	String dictName = extras.getString("dict");
        	if (dictName != null) {
        		Dictionary dict = ((KumvaApplication)getApplication()).getDictionaryByName(dictName);
        		name.setText(dict.getName());
        		name.setSummary(dict.getName());
        		url.setText(dict.getUrl());
        		url.setSummary(dict.getUrl());
        	}
        }
        
        /**
         * TODO this don't listen to nufin
         */
        getPreferenceScreen().setOnPreferenceChangeListener(this);
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
	
	private void onMenuDelete() {	
	}
	
	private void onMenuSave() {
	}

	@Override
	public boolean onPreferenceChange(Preference pref, Object value) {
		Log.e("Kumva", "onPreferenceChange");
		
		if (pref instanceof EditTextPreference) {
			EditTextPreference edit = (EditTextPreference)pref;
			edit.setSummary(edit.getText());
		}
		return true;
	}
}
