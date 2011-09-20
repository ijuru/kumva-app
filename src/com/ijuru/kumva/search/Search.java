package com.ijuru.kumva.search;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.ijuru.kumva.Definition;

public abstract class Search extends AsyncTask<String, Void, List<Definition>> {

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
	protected List<Definition> doInBackground(String... params) {
		String query = params[0];
		return doSearch(query);
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<Definition> results) {
		for (SearchListener listener : listeners)
			listener.searchFinished(this, results);
	}

	/**
	 * Overridden by search implementations
	 * @param query the search query
	 * @return
	 */
	protected abstract List<Definition> doSearch(String query);
}
