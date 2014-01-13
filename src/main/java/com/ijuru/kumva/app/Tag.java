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

/**
 * A tag
 */
public class Tag {
	private String lang;
	private String text;
	
	/**
	 * Creates a new tag
	 * @param lang the language code
	 * @param text the text
	 */
	public Tag(String lang, String text) {
		this.lang = lang;
		this.text = text;
	}

	/**
	 * Gets the tag lang
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Gets the tag text
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
