package com.ijuru.kumva.activity;

import java.util.Comparator;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.ui.DictionaryListAdapter;
import com.ijuru.kumva.util.FetchDictionaryListener;
import com.ijuru.kumva.util.FetchDictionaryTask;
import com.ijuru.kumva.util.Utils;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
			fetchDictionary(editDictionary.getUrl());
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
		final EditText txtDictUrl = new EditText(this);
		txtDictUrl.setHint(R.string.str_siteurl);
		new AlertDialog.Builder(this)
			.setTitle(R.string.str_newdictionary)
			.setView(txtDictUrl)
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
		     })
		     .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					String url = txtDictUrl.getText().toString();
					fetchDictionary(url);
				}
		     })
			.show();
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
	}
	
	/**
	 * Sorts the dictionaries alphabetically
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
		
		// Select active dictionary
    	int position = adapter.getPosition(app.getActiveDictionary());
    	if (position >= 0)
    		getListView().setItemChecked(position, true);
	}

	@Override
	public void dictionaryFetched(Dictionary dictionary) {
		KumvaApplication app = (KumvaApplication)getApplication();
		
		// Hide the progress dialog
		if (progressDialog != null)
			progressDialog.dismiss();
		
		if (dictionary != null) {
			// Look for dictionary with same URL which needs to be replaced
			for (Dictionary dict : app.getDictionaries()) {
				if (dict.getUrl().equals(dictionary.getUrl())) {
					removeDictionary(dict);
					break;
				}
			}
			
			addDictionary(dictionary);
		}
		else {
			Utils.alert(this, getString(R.string.err_communicationfailed));
		}
	}
}
