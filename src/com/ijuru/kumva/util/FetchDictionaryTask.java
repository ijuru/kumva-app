package com.ijuru.kumva.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

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
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
