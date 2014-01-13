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

package com.ijuru.kumva.app.search;

import java.util.List;

import com.ijuru.kumva.app.Entry;

/**
 * The result of a search
 */
public class SearchResult {
	private String suggestion;
	private List<Entry> matches;
	
	/**
	 * Constructs a search result
	 * @param suggestion the suggestion
	 * @param matches the matching definitions
	 */
	public SearchResult(String suggestion, List<Entry> matches) {
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
	public List<Entry> getMatches() {
		return matches;
	}
}
