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

/**
 * A search suggestion
 */
public class Suggestion {
	private String text;
	private String lang;
	
	/**
	 * Creates a suggestion
	 * @param text the text
	 * @param lang the language code
	 */
	public Suggestion(String text, String lang) {
		this.text = text;
		this.lang = lang;
	}

	/**
	 * Gets the text
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text
	 * @param text the text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the language code
	 * @return the language code
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Sets the language code
	 * @param lang the language code
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
}
