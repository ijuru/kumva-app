package com.ijuru.kumva.search;

import java.util.List;

import com.ijuru.kumva.Definition;

/**
 * The result of a search
 */
public class SearchResult {
	private String suggestion;
	private List<Definition> matches;
	
	/**
	 * Constructs a search result
	 * @param suggestion the suggestion
	 * @param matches the matching definitions
	 */
	public SearchResult(String suggestion, List<Definition> matches) {
		this.suggestion = suggestion;
		this.matches = matches;
	}

	/**
	 * Gets the suggestion or null
	 * @return the suggestion
	 */
	public String getSuggestion() {
		return suggestion;
	}

	/**
	 * Gets the matched definitions
	 * @return the definitions
	 */
	public List<Definition> getMatches() {
		return matches;
	}
}
