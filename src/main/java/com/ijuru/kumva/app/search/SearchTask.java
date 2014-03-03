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

import com.ijuru.kumva.remote.RemoteDictionary;
import com.ijuru.kumva.remote.RemoteSearch;
import com.ijuru.kumva.search.Search;
import com.ijuru.kumva.search.SearchResult;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Base class for searches
 */
public class SearchTask extends AsyncTask<Object, Void, SearchResult> {

	protected RemoteDictionary dictionary;

	protected String query;

	protected SearchListener listener;

	protected int timeout;

	/**
	 * Listener class for search tasks
	 */
	public interface SearchListener {
		/**
		 * Called when a search has successfully completed
		 * @param search the search
		 * @param result the search results
		 */
		public void onSearchCompleted(SearchTask search, SearchResult result);

		/**
		 * Called when a search resulted in an error
		 * @param search the search
		 */
		public void onSearchError(SearchTask search);
	}

	/**
	 * Creates a search on the given dictionary
	 * @param dictionary the dictionary
	 */
	public SearchTask(RemoteDictionary dictionary, String query, int timeout) {
		this.dictionary = dictionary;
		this.query = query;
		this.timeout = timeout;
	}

	/**
	 * Gets the query
	 * @return the query
	 */
	public String getQuery() {
		return query;
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
		Integer limit = (Integer) params[0];
		String ref = (String) params[1];

		Search search = new RemoteSearch(dictionary, timeout);
		try {
			return search.execute(query, limit, ref);
		}
		catch (Exception ex) {
			Log.e("Kumva", ex.getMessage(), ex);
			return null;
		}
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
}