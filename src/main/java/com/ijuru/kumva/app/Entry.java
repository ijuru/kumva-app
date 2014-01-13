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

package com.ijuru.kumva.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A dictionary entry
 */
public class Entry {
	private String wordClass;
	private List<Integer> nounClasses = new ArrayList<Integer>();
	private String prefix;
	private String lemma;
	private String modifier;
	private String pronunciation;
	private List<Meaning> meanings = new ArrayList<Meaning>();
	private String comment;
	private Map<String, List<Tag>> tags = new HashMap<String, List<Tag>>();
	private List<Example> examples = new ArrayList<Example>();
	private String audioURL;
	
	/**
	 * Gets the word class
	 * @return the wordClass
	 */
	public String getWordClass() {
		return wordClass;
	}
	
	/**
	 * Sets the word class
	 * @param wordClass the wordClass to set
	 */
	public void setWordClass(String wordClass) {
		this.wordClass = wordClass;
	}
	
	/**
	 * Gets the noun classes
	 * @return the nounClasses
	 */
	public List<Integer> getNounClasses() {
		return nounClasses;
	}
	
	/**
	 * Sets the noun classes
	 * @param nounClasses the nounClasses to set
	 */
	public void setNounClasses(List<Integer> nounClasses) {
		this.nounClasses = nounClasses;
	}
	
	/**
	 * Gets the prefix
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Sets the prefix
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Gets the lemma
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}
	
	/**
	 * Sets the lemma
	 * @param lemma the lemma to set
	 */
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	
	/**
	 * Gets the modifier e.g. the plural prefix or past tense stem
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * Sets the modifier e.g. the plural prefix or past tense stem
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	/**
	 * Gets the pronunciation
	 * @return the pronunciation
	 */
	public String getPronunciation() {
		return pronunciation;
	}
	
	/**
	 * Sets the pronunciation
	 * @param pronunciation the pronunciation
	 */
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	
	/**
	 * Gets the meanings
	 * @return the meanings
	 */
	public List<Meaning> getMeanings() {
		return meanings;
	}
	
	/**
	 * Adds a new meaning
	 * @param meaning the meaning to add
	 */
	public void addMeaning(Meaning meaning) {
		this.meanings.add(meaning);
	}
	
	/**
	 * Gets the comment
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Sets the comment
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets the tags for the given relationship
	 * @param relationship the relationship
	 * @return the tags
	 */
	public List<Tag> getTags(String relationship) {
		return tags.get(relationship);
	}
	
	/**
	 * Sets the tags for the given relationship
	 * @param relationship the relationship
	 * @param tags the tag
	 */
	public void setTags(String relationship, List<Tag> tags) {
		this.tags.put(relationship, tags);
	}
	
	/**
	 * Gets the usage examples
	 * @return the examples
	 */
	public List<Example> getExamples() {
		return examples;
	}
	
	/**
	 * Adds a new usage example
	 * @param example the example to add
	 */
	public void addExample(Example example) {
		this.examples.add(example);
	}

	/**
	 * Gets the audio URL (null if one doesn't exist)
	 * @return the audioURL
	 */
	public String getAudioURL() {
		return audioURL;
	}

	/**
	 * Sets the audio URL
	 * @param audioURL the audio URL
	 */
	public void setAudioURL(String audioURL) {
		this.audioURL = audioURL;
	}
}
