package com.ijuru.kumva.activity;

import java.util.List;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.R;
import com.ijuru.kumva.search.OnlineSearch;
import com.ijuru.kumva.ui.DefinitionListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	private DefinitionListAdapter adapter;
	private ProgressDialog progressDialog;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        ListView listResults = (ListView)findViewById(R.id.listResults);
        this.adapter = new DefinitionListAdapter(this);
        listResults.setAdapter(adapter);
    }
    
    /**
     * Performs a dictionary search
     * @param view the view
     */
    public void doSearch(View view) {
    	// Get query
    	EditText txtQuery = (EditText)findViewById(R.id.queryfield);
    	String query = txtQuery.getText().toString();
    	
    	// Hide on-screen keyboard
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(txtQuery.getWindowToken(), 0);
    	
    	progressDialog = ProgressDialog.show(this, "Kumva", "Searching...");
    	
    	Log.d("Kumva", "New query: " + query);
    	
    	adapter.clear();
    	
    	new OnlineSearch(this).execute(query);
    }

	public void searchFinished(List<Definition> results) {
		// Hide the progress dialog
		if (progressDialog != null)
			progressDialog.dismiss();
		
		for (Definition definition : results)
			adapter.add(definition);
	}
}