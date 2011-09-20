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
import com.ijuru.kumva.xml.DefinitionListener;
import com.ijuru.kumva.xml.QueryXMLHandler;

/**
 * An online search which using the Kumva XML API
 */
public class OnlineSearch extends Search implements DefinitionListener {
	
	private List<Definition> results = new ArrayList<Definition>();
	private String url;
	
	/**
	 * Constructs an online search from the given URL of a Kumva dictionary
	 * @param url the URL
	 */
	public OnlineSearch(String url) {
		this.url = url;
	}
	
	/**
	 * @see com.ijuru.kumva.search.Search#doSearch(String)
	 */
	@Override
	public List<Definition> doSearch(String query) {
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			// Create URL connection to the XML API
			String baseUrl = url + "/meta/query.xml.php?ref=android&q=";
			URL url = new URL(baseUrl + URLEncoder.encode(query));
			URLConnection connection = url.openConnection();
			
			// Request GZIP compression
			connection.setRequestProperty("Accept-Encoding", "gzip");
			
			// Detect GZIP compression if used
			InputStream stream = connection.getInputStream();
			if ("gzip".equals(connection.getContentEncoding()))
				stream = new GZIPInputStream(stream);
			
			// Start SAX parser
			InputSource source = new InputSource(stream);
			parser.parse(source, handler);
			stream.close();
			
		} catch (Exception e) {
			Log.e("Kumva", e.getMessage());
			return null;
		}
		
		return results;	
	}

	/**
	 * @see com.ijuru.kumva.xml.DefinitionListener#found(Definition)
	 */
	@Override
	public void found(Definition definition) {
		results.add(definition);
	}
}
