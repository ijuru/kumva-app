package com.ijuru.kumva;

import java.util.ArrayList;
import java.util.List;

/**
 * A word definition
 */
public class Definition {
	private String wordClass;
	private int[] nounClasses;
	private String prefix;
	private String lemma;
	private String modifier;
	private String pronunciation;
	private List<Meaning> meanings = new ArrayList<Meaning>();
	private String comment;
	
	/**
	 * @return the wordClass
	 */
	public String getWordClass() {
		return wordClass;
	}
	
	/**
	 * @param wordClass the wordClass to set
	 */
	public void setWordClass(String wordClass) {
		this.wordClass = wordClass;
	}
	
	/**
	 * @return the nounClasses
	 */
	public int[] getNounClasses() {
		return nounClasses;
	}
	
	/**
	 * @param nounClasses the nounClasses to set
	 */
	public void setNounClasses(int[] nounClasses) {
		this.nounClasses = nounClasses;
	}
	
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}
	
	/**
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
}
