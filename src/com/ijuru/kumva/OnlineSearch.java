package com.ijuru.kumva;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.util.Log;

import com.ijuru.kumva.ui.DefinitionListAdapter;
import com.ijuru.kumva.xml.DefinitionListener;
import com.ijuru.kumva.xml.QueryXMLHandler;

public class OnlineSearch implements Runnable, DefinitionListener {

	private String query;
	private DefinitionListAdapter adapter;
	
	public OnlineSearch(String query, DefinitionListAdapter adapter) {
		this.query = query;
		this.adapter = adapter;
	}
	
	@Override
	public void run() {
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			URL url = new URL("http://kinyarwanda.net/meta/query.xml.php?q=" + query);
			parser.parse(url.openStream(), handler);
			
		} catch (Exception e) {
			Log.e("Kumva", e.getMessage());
		}
	}

	@Override
	public void found(Definition definition) {
		adapter.add(definition);
	}
}
