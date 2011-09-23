package com.ijuru.kumva.util;

import com.ijuru.kumva.Dictionary;

/**
 * Listener interface for dictionary detching tasks
 */
public interface FetchDictionaryListener {
	/**
	 * Dictionary fetching is complete
	 * @param dictionary the dictionary or null if error occured
	 */
	public void dictionaryFetched(Dictionary dictionary);
}
