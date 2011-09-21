package com.ijuru.kumva.activity;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.ui.DictionaryListAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DictionariesActivity extends ListActivity {

	private DictionaryListAdapter adapter;
	
	/**
	 * Called when the activity is first created
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        this.adapter = new DictionaryListAdapter(this);
    	setListAdapter(adapter);
    	getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
    	getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dictionary dictionary = (Dictionary)parent.getItemAtPosition(position);				
				Intent intent = new Intent(getApplicationContext(), DictionaryPrefActivity.class);
				intent.putExtra("dict", dictionary.getName());
				startActivity(intent);
			}
		});
    	
    	refreshList();
    }
    
    private void refreshList() {
    	KumvaApplication app = (KumvaApplication)getApplication();
    	
    	// Clear all items
    	adapter.clear();
    	
    	// Add existing dictionaries
    	for (Dictionary dict : app.getDictionaries())
    		adapter.add(dict);
    	
    	// Select active dictionary
    	int position = adapter.getPosition(app.getActiveDictionary());
    	if (position >= 0)
    		getListView().setItemChecked(position, true);
    }
    
	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		// Refresh list in case changes we made
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
	 * Called when user selects add menu option
	 */
	private void onMenuAdd() {
		startActivity(new Intent(this, DictionaryPrefActivity.class));
	}
}
