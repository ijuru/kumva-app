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

package com.ijuru.kumva.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ijuru.kumva.Dictionary;

import android.os.AsyncTask;

/**
 * Task to fetch a dictionary's info from the a URL
 */
public class FetchDictionaryTask extends AsyncTask<String, Void, Dictionary> {

	private List<FetchDictionaryListener> listeners = new ArrayList<FetchDictionaryListener>();
	
	/**
	 * Adds a new fetch listener
	 * @param listener the listener
	 */
	public void addListener(FetchDictionaryListener listener) {
		listeners.add(listener);
	}
	
	@Override
	protected Dictionary doInBackground(String... params) {
		String url = params[0];
		Dictionary dictionary = new Dictionary(url);
		URL dictURL = dictionary.createInfoURL();
		if (dictURL == null)
			return null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(dictURL.openStream());
			Element root =document.getDocumentElement();
			Node nameNode = root.getElementsByTagName("name").item(0);
			Node versionNode = root.getElementsByTagName("kumvaversion").item(0);
			Node defLangNode = root.getElementsByTagName("definitionlang").item(0);
			Node meanLangNode = root.getElementsByTagName("meaninglang").item(0);
			
			dictionary.setName(nameNode.getFirstChild().getNodeValue());
			dictionary.setKumvaVersion(versionNode.getFirstChild().getNodeValue());
			dictionary.setDefinitionLang(defLangNode.getFirstChild().getNodeValue());
			dictionary.setMeaningLang(meanLangNode.getFirstChild().getNodeValue());
			return dictionary;
			
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Dictionary result) {
		for (FetchDictionaryListener listener : listeners)
			listener.dictionaryFetched(result);
	}
}
