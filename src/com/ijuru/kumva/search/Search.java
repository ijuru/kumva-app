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

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

/**
 * Base class for searches
 */
public abstract class Search extends AsyncTask<Object, Void, SearchResult> {

	private List<SearchListener> listeners = new ArrayList<SearchListener>();
	
	/**
	 * Adds a search listener
	 * @param listener the listener
	 */
	public void addListener(SearchListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see android.os.AsyncTask
	 */
	@Override
	protected SearchResult doInBackground(Object... params) {
		String query = (String)params[0];
		Integer limit = (Integer)params[1];
		return doSearch(query, limit);
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(SearchResult result) {
		for (SearchListener listener : listeners)
			listener.searchFinished(this, result);
	}

	/**
	 * Overridden by search implementations
	 * @param query the search query
	 * @return the search result
	 */
	protected abstract SearchResult doSearch(String query, int limit);
}
