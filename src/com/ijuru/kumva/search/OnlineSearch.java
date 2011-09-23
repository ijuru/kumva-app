package com.ijuru.kumva.search;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.xml.DefinitionListener;
import com.ijuru.kumva.xml.QueryXMLHandler;

/**
 * An online search which using the Kumva XML API
 */
public class OnlineSearch extends Search implements DefinitionListener {
	
	private List<Definition> results = new ArrayList<Definition>();
	private Dictionary dictionary;
	
	/**
	 * Constructs an online search from the given URL of a Kumva dictionary
	 * @param url the URL
	 */
	public OnlineSearch(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	
	/**
	 * @see com.ijuru.kumva.search.Search#doSearch(String)
	 */
	@Override
	public SearchResult doSearch(String query, int limit) {
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			// Create URL connection to the XML API
			URL url = dictionary.createQueryURL(query, limit);
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
			
			return new SearchResult(handler.getSuggestion(), results);	
			
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see com.ijuru.kumva.xml.DefinitionListener#found(Definition)
	 */
	@Override
	public void found(Definition definition) {
		results.add(definition);
	}
}
