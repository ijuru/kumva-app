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

package com.ijuru.kumva.activity;

import java.util.List;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.Suggestion;
import com.ijuru.kumva.search.Search;
import com.ijuru.kumva.search.SearchResult;
import com.ijuru.kumva.ui.DefinitionListAdapter;
import com.ijuru.kumva.ui.SuggestionListAdapter;
import com.ijuru.kumva.util.Dialogs;
import com.ijuru.kumva.util.FetchSuggestionsTask;
import com.ijuru.kumva.util.FetchTask;
import com.ijuru.kumva.util.FetchTask.FetchListener;
import com.ijuru.kumva.util.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Main activity for searching a dictionary
 */
public class SearchActivity extends Activity 
		implements AdapterView.OnItemClickListener, Search.SearchListener, OnCancelListener, FetchListener<List<Suggestion>> {
	
	private DefinitionListAdapter definitionAdapter;
	private SuggestionListAdapter suggestionAdapter;
	private ProgressDialog progressDialog;
	private String suggestionsTerm;
	private FetchSuggestionsTask suggestionsTask;
	private Search search;
	
    /**
     * Called when the activity is first created
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_search);
        
        EditText txtQuery = (EditText)findViewById(R.id.queryfield);
        
        this.definitionAdapter = new DefinitionListAdapter(this);
        this.suggestionAdapter = new SuggestionListAdapter(this);
        
        final int minSuggLen = getResources().getInteger(R.integer.min_autocomplete_query_chars);
        
        txtQuery.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				// Only intercept printable characters
				if (event.getUnicodeChar() > 0) {
					String text = ((TextView)view).getText().toString();
					if (text.length() >= minSuggLen && !text.equals(suggestionsTerm)) {
						suggestionsTerm = text;
						doSuggestions(text);
					}
				}
				return false;
			}
		});
        
        txtQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getAction() == KeyEvent.ACTION_DOWN) 
					onSearchClick(null);

				return true;
			}
		});
        
        ListView listResults = (ListView)findViewById(R.id.listresults);
        listResults.setOnItemClickListener(this);
        
        // Check for an initial query passed via the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
	        String initQuery = (String)extras.get("query");
	        if (initQuery != null)
	        	doSearch(initQuery);
        }
    }
    
    /**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		// In case active dictionary was changed
		updateControls();
	}
	
	/**
	 * Updates controls which depend on the active dictionary
	 */
	private void updateControls() {
		KumvaApplication app = (KumvaApplication)getApplication();
		EditText txtQuery = (EditText)findViewById(R.id.queryfield);
		
		// Create query field hint from active dictionary's languages
        Dictionary dictionary = app.getActiveDictionary();
        if (dictionary != null) {
        	String lang1 = Utils.getLanguageName(dictionary.getDefinitionLang());
        	String lang2 = Utils.getLanguageName(dictionary.getMeaningLang());
        	String hint = String.format(getString(R.string.str_searchhint), lang1, lang2);
        	txtQuery.setHint(hint);
        	txtQuery.setEnabled(true);
        }
        else {
        	txtQuery.setHint(R.string.str_nodictionary);
        	txtQuery.setEnabled(false);
        }
	}
    
    /**
     * Performs a dictionary search (called from event therefore must be public)
     * @param view the view
     */
    private void doSearch(String query) {
    	// Cancel any existing suggestion fetch
		if (suggestionsTask != null)
			suggestionsTask.cancel(true);
		
    	// Switch to definition list view
    	ListView listResults = (ListView)findViewById(R.id.listresults);
    	listResults.setAdapter(definitionAdapter);
    	
    	// Clear status message
    	setStatusMessage(null);
    	
    	// Set query field and move cursor to end
    	EditText txtQuery = (EditText)findViewById(R.id.queryfield);
    	txtQuery.setText(query);
    	txtQuery.setSelection(query.length());
    	
    	// Hide on-screen keyboard
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(txtQuery.getWindowToken(), 0);
    	
    	definitionAdapter.clear();
    	
    	// Initiate search of the active dictionary
    	KumvaApplication app = (KumvaApplication) getApplication();
    	Dictionary activeDictionary = app.getActiveDictionary();
    	
    	if (activeDictionary != null) {
    		progressDialog = ProgressDialog.show(this, getString(R.string.str_searching), getString(R.string.str_pleasewait));
    		progressDialog.setCancelable(true);
    		progressDialog.setOnCancelListener(this);
    		
    		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    		Integer limit = Utils.parseInteger(prefs.getString("max_results", "50"));
    		
    		int timeout = getResources().getInteger(R.integer.connection_timeout);
    		
	    	search = activeDictionary.createSearch(timeout);
	    	search.setListener(this);
	    	search.execute(query, limit);
    	}
    	else
    		Dialogs.error(this, getString(R.string.err_nodictionary));
    }

	/**
     * Called when search completes successfully
     */
    @Override
	public void onSearchCompleted(Search search, SearchResult result) {
		// Hide the progress dialog
    	progressDialog.dismiss();
			
		if (result.getMatches().size() == 0) {
			// Tell user no results... sorry
			setStatusMessage(getString(R.string.str_noresults));
		}
		else {
			for (Definition definition : result.getMatches())
				definitionAdapter.add(definition);
			
			if (!Utils.isEmpty(result.getSuggestion())) {
				// Update status message to show user the search suggestion
				String message = String.format(getString(R.string.str_bysuggestion), result.getSuggestion());
				setStatusMessage(message);
			}
		}
	}
    
    /**
     * Called if search fails
     */
    @Override
	public void onSearchError(Search search) {
    	// Hide the progress dialog and display error message
    	progressDialog.dismiss();
    	Dialogs.toast(this, getString(R.string.err_communicationfailed));
	}
    
    /**
     * Performs a lookup of search suggestions
     * @param query the partial query
     */
    private void doSuggestions(String query) {
    	// Switch to suggestion list view
    	ListView listResults = (ListView)findViewById(R.id.listresults);
    	listResults.setAdapter(suggestionAdapter);
    	
    	KumvaApplication app = (KumvaApplication) getApplication();
    	Dictionary activeDictionary = app.getActiveDictionary();
    	
    	if (activeDictionary != null) {
    		// Cancel existing suggestion fetch
    		if (suggestionsTask != null)
    			suggestionsTask.cancel(true);
    		
    		int timeout = getResources().getInteger(R.integer.connection_timeout);
    		suggestionsTask = new FetchSuggestionsTask(activeDictionary, timeout);
    		suggestionsTask.execute(query);
    		suggestionsTask.setListener(this);
    	}
	}
	
    /**
     * Called if suggestion fetch succeeds
     */
	@Override
	public void onFetchCompleted(FetchTask<List<Suggestion>> task, List<Suggestion> result) {
		suggestionAdapter.clear();
		
		// Add suggestions to list
		for (Suggestion suggestion : result)
			suggestionAdapter.add(suggestion);
	}
	
	/**
     * Called if suggestion fetch fails
     */
	@Override
	public void onFetchError(FetchTask<List<Suggestion>> task) {
		// Awww
	}
	
	/**
     * Sets the status message under the query field, or hides it
     * @param message the message or null to hide it
     */
	private void setStatusMessage(String message) {
		TextView txtStatus = (TextView)findViewById(R.id.statusmessage);
  
    	if (message == null) 
    		txtStatus.setVisibility(View.GONE);
    	else {
    		txtStatus.setText(message);
	    	txtStatus.setVisibility(View.VISIBLE);
    	}
	}
	
	/**
	 * Called when the search button is clicked
	 * @param view the view
	 */
	public void onSearchClick(View view) {
		// Get query field
    	EditText txtQuery = (EditText)findViewById(R.id.queryfield);
    	String query = txtQuery.getText().toString();
    	
		doSearch(query);
	}
	
	/**
	 * Called when a list item is selected (either a definition or suggestion)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object item = parent.getItemAtPosition(position);
		if (item instanceof Definition) {
			Definition definition = (Definition)item;
			((KumvaApplication)getApplication()).setCurrentDefinition(definition);
			
			Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
			startActivity(intent);
		}
		else if (item instanceof Suggestion) {
			Suggestion suggestion = (Suggestion)item;
			doSearch(suggestion.getText());
		}
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search, menu);
	    return true;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menudictionaries:
			startActivity(new Intent(getApplicationContext(), DictionariesActivity.class));
	    	break;
		case R.id.menupreferences:
			startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
	    	break;
	    case R.id.menuabout:
	    	onMenuAbout();
	    	break;
		}
		return true;
	}

	/**
	 * Displays the about dialog
	 */
	private void onMenuAbout() {
		String title = getString(R.string.app_name) + " " + Utils.getVersionName(this);
		String message = "Thank you for downloading Kumva\n" +
				"\n" +
				"If you have any problems please contact rowan@ijuru.com";
		
		Dialogs.alert(this, title, message);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		search.cancel(true);
	}
}