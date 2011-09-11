package com.ijuru.kumva;

public class Example {
	private String usage;
	private String meaning;
	
	public Example() {
	}
	
	public Example(String usage, String meaning) {
		this.usage = usage;
		this.meaning = meaning;
	}

	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}

	/**
	 * @return the meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * @param meaning the meaning to set
	 */
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
