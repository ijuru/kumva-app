package com.ijuru.kumva.util;

import android.os.AsyncTask;

/**
 * Base class for async tasks that fetch data from URLs
 * @param <T> the return type of the task
 */
public abstract class FetchTask<T> extends AsyncTask<String, Void, T> {

	private OnCompleteListener<T> completeListener;
	private OnErrorListener<T> errorListener;
	
	/**
	 * Listener for successful completion of the task
	 */
	public interface OnCompleteListener<T> {
		/**
		 * Item fetching is complete
		 * @param result the resulting object
		 */
		public void onFetchComplete(T result);
	}
	
	/**
	 * Listener for errors during the task
	 */
	public interface OnErrorListener<T> {
		/**
		 * Item fetching resulted in an error
		 */
		public void onFetchError();
	}
	
	/**
	 * Sets the completed listener
	 * @param listener the listener
	 */
	public void setOnCompletedListener(OnCompleteListener<T> listener) {
		this.completeListener = listener;
	}
	
	/**
	 * Sets the error listener
	 * @param listener the listener
	 */
	public void setOnErrorListener(OnErrorListener<T> listener) {
		this.errorListener = listener;
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
		if (result != null && completeListener != null)
			completeListener.onFetchComplete(result);
		else if (errorListener != null)
			errorListener.onFetchError();
	}
	
	/**
	 * Overridden by sub classes to perform the fetching
	 * @param url the URL
	 * @return the fetched object
	 */
	protected abstract T fetch(String url);
}
