package com.ijuru.kumva;

import com.ijuru.kumva.search.OnlineSearch;
import com.ijuru.kumva.search.Search;

/**
 * An online Kumva dictionary
 */
public class Dictionary {
	private String name;
	private String url;
	private String definitionLang;
	private String meaningLang;
	
	/**
	 * Constructs a dictionary
	 * @param name the name
	 * @param url the base URL
	 */
	public Dictionary(String name, String url, String definitionLang, String meaningLang) {
		this.name = name;
		this.url = url;
		this.definitionLang = definitionLang;
		this.meaningLang = meaningLang;
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
	 * Gets the language code of definitions
	 * @return the definitionLanguage
	 */
	public String getDefinitionLang() {
		return definitionLang;
	}

	/**
	 * Sets the language code of definitions
	 * @param definitionLang the language code
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
	 * @param meaningLang the language code
	 */
	public void setMeaningLanguage(String meaningLang) {
		this.meaningLang = meaningLang;
	}

	/**
	 * Creates a new search which can query this dictionary
	 * @return the search
	 */
	public Search createSearch() {
		return new OnlineSearch(this.url);
	}
}
