/**
 * Copyright 2011 Rowan Seymour
 * 
 * This file is part of Kumva.
 *
 * Kumva is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kumva is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kumva. If not, see <http://www.gnu.org/licenses/>.
 */

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
		/*if (query.equals("@@test")) {
			Definition def = new Definition();
			def.setWordClass("n");
			def.setPrefix("ama");
			def.setLemma("testi");
			def.setModifier("ubu-");
			def.addMeaning(new Meaning("I'm secret just for testing", 0));
			def.addMeaning(new Meaning("Cuz the internet doesn't always work", 0));
			def.setAudioURL("http://kinyarwanda.net/media/audio/1921.mp3");
			results.add(def);
			return new SearchResult(null, results);
		}*/
		
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			QueryXMLHandler handler = new QueryXMLHandler();
			
			handler.addListener(this);
			
			// Create URL connection to the XML API
			URL url = dictionary.createQueryURL(query, limit);
			URLConnection connection = url.openConnection();
			
			// Request GZIP compression and specify timeout
			connection.setRequestProperty("Accept-Encoding", "gzip");
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			
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
