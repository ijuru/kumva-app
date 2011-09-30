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

package com.ijuru.kumva;

import java.util.ArrayList;
import java.util.List;

/**
 * A word definition
 */
public class Definition {
	private String wordClass;
	private List<Integer> nounClasses = new ArrayList<Integer>();
	private String prefix;
	private String lemma;
	private String modifier;
	private String pronunciation;
	private List<Meaning> meanings = new ArrayList<Meaning>();
	private String comment;
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
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	/**
	 * @return the pronunciation
	 */
	public String getPronunciation() {
		return pronunciation;
	}
	
	/**
	 * @param pronunciation the pronunciation to set
	 */
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	
	/**
	 * @return the meanings
	 */
	public List<Meaning> getMeanings() {
		return meanings;
	}
	
	/**
	 * @param meaning the meaning to add
	 */
	public void addMeaning(Meaning meaning) {
		this.meanings.add(meaning);
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * @return the examples
	 */
	public List<Example> getExamples() {
		return examples;
	}
	
	/**
	 * @param example the example to add
	 */
	public void addExample(Example example) {
		this.examples.add(example);
	}

	/**
	 * Gets the audio URL
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
