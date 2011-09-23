package com.ijuru.kumva.search;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

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
