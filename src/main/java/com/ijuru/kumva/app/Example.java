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

public class Example {
	private String usage;
	private String meaning;
	
	/**
	 * Default constructor
	 */
	public Example() {
	}
	
	/**
	 * Creates an example
	 * @param usage
	 * @param meaning
	 */
	public Example(String usage, String meaning) {
		this.usage = usage;
		this.meaning = meaning;
	}

	/**
	 * Gets the usage
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * Sets the usage
	 * @param usage the usage
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}

	/**
	 * Gets the meaning
	 * @return the meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * Sets the meaning
	 * @param meaning the meaning
	 */
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
