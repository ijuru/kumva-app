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

package com.ijuru.kumva.app.site;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.ijuru.kumva.app.search.OnlineSearch;
import com.ijuru.kumva.app.search.Search;

/**
 * An online Kumva dictionary
 */
public class Dictionary {
	private String url;
	private String name;
	private String kumvaVersion;
	private String definitionLang;
	private String meaningLang;
	
	/**
	 * Constructs a dictionary
	 * @param url the URL
	 */
	public Dictionary(String url) {
		this.url = url;
	}
	
	/**
	 * Constructs a dictionary
	 * @param url the base URL
	 * @param name the name
	 */
	public Dictionary(String url, String name, String kumvaVersion, String definitionLang, String meaningLang) {
		this.url = url;
		this.name = name;
		this.kumvaVersion = kumvaVersion;
		this.definitionLang = definitionLang;
		this.meaningLang = meaningLang;
	}
	
	/**
	 * Gets the URL
	 * @return the url
	 */
	public String getURL() {
		return url;
	}
	
	/**
	 * Sets the URL
	 * @param url the url
	 */
	public void setURL(String url) {
		this.url = url;
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the Kumva version string
	 * @return the kumvaVersion
	 */
	public String getKumvaVersion() {
		return kumvaVersion;
	}

	/**
	 * Sets the Kumva version
	 * @param kumvaVersion the version
	 */
	public void setKumvaVersion(String kumvaVersion) {
		this.kumvaVersion = kumvaVersion;
	}

	/**
	 * Gets the language code of definitions
	 * @return the definitionLanguage
	 */
	public String getDefinitionLang() {
		return definitionLang;
	}

	/**
	 * Sets the language code of definitions
	 * @param definitionLang the code
	 */
	public void setDefinitionLang(String definitionLang) {
		this.definitionLang = definitionLang;
	}

	/**
	 * @return the meaningLang
	 */
	public String getMeaningLang() {
		return meaningLang;
	}
	
	/**
	 * Sets the language code of meanings
	 * @param meaningLang the code
	 */
	public void setMeaningLang(String meaningLang) {
		this.meaningLang = meaningLang;
	}
	
	/**
	 * Creates a URL to query information about this dictionary
	 * @return the URL
	 */
	public URL createInfoURL() {
		return createURL("meta/site.xml.php");
	}
	
	/**
	 * Creates a URL to query this dictionary
	 * @param query the query
	 * @param limit the maximum results
	 * @return the URL
	 */
	public URL createQueryURL(String query, int limit) {
		return createURL("meta/query.xml.php?q=" + URLEncoder.encode(query) + "&limit=" + limit + "&entries=1&ref=android");
	}
	
	/**
	 * Creates a URL to get search suggestions from this dictionary
	 * @param query the query
	 * @return the URL
	 */
	public URL createSuggestionsURL(String query) {
		return createURL("meta/suggest.php?term=" + URLEncoder.encode(query) + "&format=jquery");
	}
	
	/**
	 * Creates a URL to a relative URL in this dictionary
	 * @param relUrl the relative URL
	 * @return the URL
	 */
	private URL createURL(String relUrl) {
		String base = this.url.endsWith("/") ? this.url : (this.url + "/");
		try {
			return new URL(base + relUrl);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Creates a new search which can query this dictionary
	 * @param timeout the timeout
	 * @return the search
	 */
	public Search createSearch(int timeout) {
		return new OnlineSearch(this, timeout);
	}

	/**
	 * Creates a CSV representation of this dictionary's properties
	 */
	@Override
	public String toString() {
		String name = this.name.replace(",", "");
		return url + "," + name + "," + kumvaVersion + "," + definitionLang + "," + meaningLang;
	}	
}
