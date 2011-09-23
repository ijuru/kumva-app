package com.ijuru.kumva.activity;

import java.util.Comparator;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.ui.DictionaryListAdapter;
import com.ijuru.kumva.util.Dialogs;
import com.ijuru.kumva.util.FetchDictionaryListener;
import com.ijuru.kumva.util.FetchDictionaryTask;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DictionariesActivity extends ListActivity implements FetchDictionaryListener {

	private DictionaryListAdapter adapter;
	private Dictionary editDictionary;
	private final int MENU_UPDATE = 0;
	private final int MENU_REMOVE = 1;
	private ProgressDialog progressDialog;
	
	/**
	 * Called when the activity is first created
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final KumvaApplication app = (KumvaApplication)getApplication();
    	
        this.adapter = new DictionaryListAdapter(this);
    	setListAdapter(adapter);
    	getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	
    	// Handle selection/activation of a dictionary
    	getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dictionary dictionary = adapter.getItem(position);
				app.setActiveDictionary(dictionary);
			}
		});
    	
    	// Handle long click of a dictionary
    	registerForContextMenu(getListView());
    			
    	// Load dictionaries into list
    	refreshList();
    }

	/**
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		
		KumvaApplication app = (KumvaApplication)getApplication();
		app.saveDictionaries();
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dictionaries, menu);
	    return true;
	}
	
	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menudictadd:
			onMenuAdd();
		}
		return true;
	}
	
	/**
	 * @see android.app.Activity#onCreateContextMenu(ContextMenu, View, ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		this.editDictionary = adapter.getItem(info.position); 
		menu.setHeaderTitle(editDictionary.getName());
		menu.add(Menu.NONE, MENU_UPDATE, 0, R.string.str_update);
		menu.add(Menu.NONE, MENU_REMOVE, 1, R.string.str_remove);
	}

	/**
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_UPDATE:
			fetchDictionary(editDictionary.getURL());
			break;
		case MENU_REMOVE:
			removeDictionary(editDictionary);
			break;
		}
		
		return true;
	}

	/**
	 * Called when user selects add menu option
	 */
	private void onMenuAdd() {		
		Dialogs.prompt(this, getString(R.string.str_newdictionary), getString(R.string.str_siteurl), InputType.TYPE_TEXT_VARIATION_URI,
			new Dialogs.InputListener() {
				@Override
				public void entered(String url) {
					// Check for a duplicate with the same URL
					KumvaApplication app = (KumvaApplication)getApplication();
					Dictionary existing = app.getDictionaryByURL(url);
					if (existing == null)
						fetchDictionary(url);
					else
						Dialogs.toast(DictionariesActivity.this, getString(R.string.err_dictduplicate));
				}
		    });
	}
	
	/**
	 * Adds a dictionary by its URL
	 * @param url the URL of the dictionary site
	 */
	private void fetchDictionary(String url) {
		progressDialog = ProgressDialog.show(this, getString(R.string.str_fetching), getString(R.string.str_pleasewait));
		
		FetchDictionaryTask task = new FetchDictionaryTask();
		task.addListener(this);
		task.execute(url);
	}
	
	/**
	 * Adds the specified dictionary
	 * @param dictionary the dictionary
	 */
	private void addDictionary(Dictionary dictionary) {
		KumvaApplication app = (KumvaApplication)getApplication();
		adapter.add(dictionary);
		app.addDictionary(dictionary);
		
		refreshList();
	}
	
	/**
	 * Removes the specified dictionary
	 * @param dictionary the dictionary
	 */
	private void removeDictionary(Dictionary dictionary) {
		KumvaApplication app = (KumvaApplication)getApplication();
		app.removeDictionary(editDictionary);
		adapter.remove(editDictionary);
		
		refreshList();
	}
	
	/**
	 * Recreates the list of dictionaries
	 */
	private void refreshList() {
		KumvaApplication app = (KumvaApplication)getApplication();
		
		// Clear all items
    	adapter.clear();
    	
    	// Add existing dictionaries
    	for (Dictionary dict : app.getDictionaries())
    		adapter.add(dict);
    			
    	adapter.sort(new Comparator<Dictionary>() {
			@Override
			public int compare(Dictionary d1, Dictionary d2) {
				return d1.getName().compareTo(d2.getName());
		}});
		
		// Select active dictionary if there is one
    	if (app.getActiveDictionary() != null) {
	    	int position = adapter.getPosition(app.getActiveDictionary());
	    	if (position >= 0)
	    		getListView().setItemChecked(position, true);
    	}
	}

	/**
	 * @see com.ijuru.kumva.util.FetchDictionaryListener#dictionaryFetched(Dictionary)
	 */
	@Override
	public void dictionaryFetched(Dictionary dictionary) {
		KumvaApplication app = (KumvaApplication)getApplication();
		
		// Hide the progress dialog
		if (progressDialog != null)
			progressDialog.dismiss();
		
		if (dictionary != null) {
			// Look for dictionary with same URL which needs to be replaced
			Dictionary existing = app.getDictionaryByURL(dictionary.getURL());
			removeDictionary(existing);
			
			addDictionary(dictionary);
		}
		else
			Dialogs.toast(this, getString(R.string.err_communicationfailed));
	}
}
