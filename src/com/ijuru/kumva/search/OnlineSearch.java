package com.ijuru.kumva.search;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

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
			
			// Create URL connection to the XML API
			String baseUrl = SITE_URL + "/meta/query.xml.php?ref=android&q=";
			URL url = new URL(baseUrl + URLEncoder.encode(query));
			URLConnection connection = url.openConnection();
			
			// Detect GZIP compression if used
			InputStream stream = connection.getInputStream();
			if ("gzip".equals(connection.getContentEncoding())) {
			  stream = new GZIPInputStream(stream);
			}
			
			// Start SAX parser
			InputSource source = new InputSource(stream);
			parser.parse(source, handler);
			stream.close();
			
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
