package com.ijuru.kumva;

import com.ijuru.kumva.search.OnlineSearch;
import com.ijuru.kumva.search.Search;

/**
 * An online Kumva dictionary
 */
public class Dictionary {
	private String name;
	private String url;
	
	/**
	 * Constructs a dictionary
	 * @param name the name
	 * @param url the base URL
	 */
	public Dictionary(String name, String url) {
		this.name = name;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the URL
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the URL
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}	
	
	/**
	 * Creates a new search which can query this dictionary
	 * @return the search
	 */
	public Search createSearch() {
		return new OnlineSearch(this.url);
	}
}
