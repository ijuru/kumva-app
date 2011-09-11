package com.ijuru.kumva;

public class Meaning {
	private String text;
	private int flags;
	
	public Meaning() {
	}
	
	public Meaning(String text, int flags) {
		this.text = text;
		this.flags = flags;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}
}
