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

import com.ijuru.kumva.app.site.Dictionary;

import android.os.AsyncTask;

/**
 * Base class for searches
 */
public abstract class Search extends AsyncTask<Object, Void, SearchResult> {

	protected Dictionary dictionary;
	protected SearchListener listener;
	
	/**
	 * Listener class for search tasks
	 */
	public interface SearchListener {
		/**
		 * Called when a search has successfully completed
		 * @param search the search
		 * @param result the search results
		 */
		public void onSearchCompleted(Search search, SearchResult result);
		
		/**
		 * Called when a search resulted in an error
		 * @param search the search
		 */
		public void onSearchError(Search search);
	}
	
	/**
	 * Creates a search on the given dictionary
	 * @param dictionary the dictionary
	 */
	public Search(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	/**
	 * Sets the search listener
	 * @param listener the listener
	 */
	public void setListener(SearchListener listener) {
		this.listener = listener;
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
		if (listener != null) {
			if (result != null)
				listener.onSearchCompleted(this, result);
			else
				listener.onSearchError(this);
		}
	}

	/**
	 * Overridden by search implementations
	 * @param query the search query
	 * @return the search result
	 */
	protected abstract SearchResult doSearch(String query, int limit);
}
