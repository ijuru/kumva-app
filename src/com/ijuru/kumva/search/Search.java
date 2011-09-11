package com.ijuru.kumva.search;

import java.util.List;

import android.os.AsyncTask;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.activity.SearchActivity;

public abstract class Search extends AsyncTask<String, Void, List<Definition>> {

	private SearchActivity activity;
	
	public Search(SearchActivity activity) {
		this.activity = activity;
	}

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
		activity.searchFinished(results);
	}

	protected abstract List<Definition> doSearch(String query);
}
