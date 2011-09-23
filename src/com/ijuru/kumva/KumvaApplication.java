package com.ijuru.kumva;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

/**
 * The main Kumva application
 */
public class KumvaApplication extends Application {
	
	private List<Dictionary> dictionaries = new ArrayList<Dictionary>();
	private Dictionary activeDictionary = null;
	private Definition definition;
	
	/**
	 * Constructs the Kumva application
	 */
	public KumvaApplication() {
		// Add kinyarwanda.net
		Dictionary kinyaDict = new Dictionary("http://kinyarwanda.net", "Kinyarwanda.net", "?", "rw", "en");
		
		dictionaries.add(kinyaDict);
		this.activeDictionary = kinyaDict;
	}

	/**
	 * Gets the list of dictionaries available
	 * @return the dictionaries
	 */
	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}
	
	/**
	 * Gets the dictionary with the given name
	 * @return the dictionary or null
	 */
	public Dictionary getDictionaryByName(String name) {
		for (Dictionary dict : dictionaries)
			if (name.equals(dict.getName()))
				return dict;
		return null;
	}

	/**
	 * Gets the active dictionary
	 * @return the active dictionary
	 */
	public Dictionary getActiveDictionary() {
		return this.activeDictionary;
	}
	
	/**
	 * Sets the active dictionary
	 * @param dictionary the active dictionary
	 */
	public void setActiveDictionary(Dictionary dictionary) {
		this.activeDictionary = dictionary;
	}
	
	/**
	 * Adds the specified dictionary
	 * @param dictionary the dictionary to add
	 */
	public void addDictionary(Dictionary dictionary) {
		this.dictionaries.add(dictionary);
	}
	
	/**
	 * Removes the specified dictionary
	 * @param dictionary the dictionary to delete
	 */
	public void removeDictionary(Dictionary dictionary) {
		this.dictionaries.remove(dictionary);
	}

	/**
	 * Gets the currently viewed definition
	 * @return the definition
	 */
	public Definition getCurrentDefinition() {
		return definition;
	}

	/**
	 * Sets the currently viewed definition
	 * @param definition the definition to set
	 */
	public void setCurrentDefinition(Definition definition) {
		this.definition = definition;
	}
}
