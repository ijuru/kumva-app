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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Task to fetch suggestions for a search term
 */
public class FetchSuggestionsTask extends FetchTask<List<Suggestion>> {

	private Dictionary dictionary;
	private int timeout;
	
	/**
	 * Creates a feth suggestions task
	 * @param dictionary the dictionary
	 * @param timeout the timeout in milliseconds
	 */
	public FetchSuggestionsTask(Dictionary dictionary, int timeout) {
		this.dictionary = dictionary;
		this.timeout = timeout;
	}
	
	@Override
	protected List<Suggestion> fetch(String term) {
		URL url = dictionary.createSuggestionsURL(term);
		
		URLConnection connection;
		try {
			connection = url.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			
			while ((line = reader.readLine()) != null)
				sb.append(line);
			
			JSONArray jsonArray = new JSONArray(sb.toString());
			List<Suggestion> suggestions = new ArrayList<Suggestion>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String text = jsonObject.getString("value");
				String lang = jsonObject.getString("lang");
				suggestions.add(new Suggestion(text, lang));
			}
				
			return suggestions;
			
		} catch (Exception e) {
			return null;
		}
	}
}
