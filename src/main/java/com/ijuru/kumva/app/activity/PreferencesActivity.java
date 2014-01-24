/**
 * Copyright 2014 Rowan Seymour
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

import com.ijuru.kumva.app.R;
import com.ijuru.kumva.app.util.Utils;

import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Preferences activity screen
 */
public class PreferencesActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	private EditTextPreference maxResultsPref;
	
	/**
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		maxResultsPref = (EditTextPreference)getPreferenceScreen().findPreference("max_results");
		maxResultsPref.setOnPreferenceChangeListener(this);
		maxResultsPref.setSummary(maxResultsPref.getText());
	}

	@Override
	public boolean onPreferenceChange(Preference pref, Object value) {
		Resources res = getResources();
		
		if (maxResultsPref == pref) {
			Integer max = Utils.parseInteger(value.toString());
			if (max == null || max < res.getInteger(R.integer.min_max_results) || max > res.getInteger(R.integer.max_max_results)) {
				String message = String.format(getString(R.string.err_numberrange), res.getInteger(R.integer.min_max_results), res.getInteger(R.integer.max_max_results));
				Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		
		pref.setSummary(value.toString());
		return true;
	}
}
