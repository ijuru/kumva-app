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

package com.ijuru.kumva.app.site;

import android.os.AsyncTask;

/**
 * Base class for async tasks that fetch data from URLs
 * @param <T> the return type of the task
 */
public abstract class FetchTask<T> extends AsyncTask<String, Void, T> {

	private FetchListener<T> listener;
	
	/**
	 * Listener for completion of the task
	 */
	public interface FetchListener<T> {
		/**
		 * Item fetching is complete
		 * @param task the task
		 * @param result the resulting object
		 */
		public void onFetchCompleted(FetchTask<T> task, T result);
		
		/**
		 * Item fetching resulted in an error
		 * @param task the task
		 */
		public void onFetchError(FetchTask<T> task);
	}
	
	/**
	 * Sets the listener
	 * @param listener the listener
	 */
	public void setListener(FetchListener<T> listener) {
		this.listener = listener;
	}
	
	@Override
	protected T doInBackground(String... params) {
		return fetch(params[0]);
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(T result) {
		if (listener != null) {
			if (result != null)
				listener.onFetchCompleted(this, result);
			else
				listener.onFetchError(this);
		}
	}
	
	/**
	 * Overridden by sub classes to perform the fetching
	 * @param url the URL
	 * @return the fetched object
	 */
	protected abstract T fetch(String url);
}
