package com.ijuru.kumva.activity;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.R;
import com.ijuru.kumva.ui.DefinitionListAdapter;
import com.ijuru.kumva.xml.QueryXMLHandler;
import com.ijuru.kumva.xml.DefinitionListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity implements DefinitionListener {
	private DefinitionListAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView listResults = (ListView)findViewById(R.id.listView1);	 	
        this.adapter = new DefinitionListAdapter(this);
        listResults.setAdapter(adapter);
    }
    
    public void doSearch(View view) {
    	EditText txtSearch = (EditText)findViewById(R.id.editText1);
    	String query = txtSearch.getText().toString();
    	
    	adapter.clear();
    	
    	try {
			URL url = new URL("http://kinyarwanda.net/meta/query.xml.php?q=" + query);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			parser.parse(url.openStream(), handler);
			
		} catch (Exception e) {
			new AlertDialog.Builder(this).setMessage(e.getMessage()).show();
		}
    }

	@Override
	public void found(Definition definition) {
		adapter.add(definition);
	}
}