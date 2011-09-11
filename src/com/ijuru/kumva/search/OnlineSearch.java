package com.ijuru.kumva.search;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.util.Log;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.activity.SearchActivity;
import com.ijuru.kumva.xml.DefinitionListener;
import com.ijuru.kumva.xml.QueryXMLHandler;

public class OnlineSearch extends Search implements DefinitionListener {
	
	private List<Definition> results = new ArrayList<Definition>();
	private static final String SITE_URL = "http://kinyarwanda.net";
	
	public OnlineSearch(SearchActivity activity) {
		super(activity);
	}
	
	@Override
	public List<Definition> doSearch(String query) {
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			String baseUrl = SITE_URL + "/meta/query.xml.php?q=";
			
			URL url = new URL(baseUrl + URLEncoder.encode(query));
			parser.parse(url.openStream(), handler);
			
		} catch (Exception e) {
			Log.e("Kumva", e.getMessage());
			//notifyFailed();
			return null;
		}
		
		//notifyFinished();
		return results;	
	}

	@Override
	public void found(Definition definition) {
		results.add(definition);
	}
}
