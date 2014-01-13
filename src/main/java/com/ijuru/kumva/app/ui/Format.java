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

package com.ijuru.kumva.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.ijuru.kumva.app.Example;
import com.ijuru.kumva.app.Meaning;
import com.ijuru.kumva.app.Tag;
import com.ijuru.kumva.app.util.Utils;

/**
 * Utility class for string/spannable formatting methods
 */
public class Format {
	/**
	 * Creates a spannable of a single query
	 * @param context the context
	 * @param query the query
	 * @return the spannable
	 */
	public static Spannable queryLink(final Context context, final String query) {
		SpannableString spannable = new SpannableString(query);
		spannable.setSpan(new QueryLinkSpan(context, query), 0, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannable;
	}
	
	/**
	 * Parses a string to create query links from {?} style references
	 * @param context the context
	 * @param text the text to parse
	 * @return the text with links
	 */
	public static Spannable parseQueryLinks(final Context context, String text) {
		if (text == null)
			return null;
		
		SpannableStringBuilder sbText = new SpannableStringBuilder();
		StringBuilder sbQuery = null;
		for (int c = 0; c < text.length(); ++c) {
			char ch = text.charAt(c);
			if (ch == '{') 
				sbQuery = new StringBuilder();
			else if (sbQuery != null) {
				if (ch == '}') {
					sbText.append(queryLink(context, sbQuery.toString()));
					sbQuery = null;
				}
				else
					sbQuery.append(ch);
			}
			else
				sbText.append(ch);
		}
		return sbText;
	}
	
	/**
	 * Formats the roots into a spannable string of links
	 * @param context the context
	 * @param defLang the active dictionary's definition language
	 * @param tags the root tags
	 * @return the spannable string
	 */
	public static Spannable rootList(Context context, String defLang, List<Tag> tags) {
		if (tags == null || tags.size() == 0)
			return null;
		
		SpannableStringBuilder sb = new SpannableStringBuilder();
		
		for (int t = 0; t < tags.size(); ++t) {
			Tag tag = tags.get(t);
			if (t > 0)
				sb.append(", ");
			
			if (tag.getLang().equals(defLang))
				sb.append(queryLink(context, tag.getText()));
			else
				sb.append(Utils.capitalize(tag.getLang()) + ". " + tag.getText());
		}
		
		return sb;
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param context the context
	 * @param meanings the meanings
	 * @param showFlags true if meaning flags should be displayed
	 * @return the formatted string
	 */
	public static Spannable meanings(Context context, List<Meaning> meanings, boolean showFlags) {
		// Convert meanings to spannables with parsed references
		List<Spannable> parsedMeanings = new ArrayList<Spannable>();
		for (Meaning meaning : meanings)
			parsedMeanings.add(parseQueryLinks(context, meaning.getText()));
				
		// Append flags to meanings
		if (showFlags) {
			List<Spannable> flaggedMeanings = new ArrayList<Spannable>();
			for (int m = 0; m < parsedMeanings.size(); ++m) {
				Meaning meaning = meanings.get(m);
				int flags = meaning.getFlags();
				if (flags > 0) {
					SpannableStringBuilder flaggedMeaning = new SpannableStringBuilder(parsedMeanings.get(m));
					flaggedMeaning.append(" (");
					flaggedMeaning.append(Meaning.makeFlagsCSV(flags));
					flaggedMeaning.append(")");
					flaggedMeanings.add(flaggedMeaning);
				}
				else
					flaggedMeanings.add(parsedMeanings.get(m));
			}
			
			parsedMeanings = flaggedMeanings;
		}
		
		// If there is only one meaning then just return it
		if (parsedMeanings.size() == 1)
			return parsedMeanings.get(0);
		
		// Otherwise create numeric list
		SpannableStringBuilder sb = new SpannableStringBuilder();
		for (int m = 0; m < parsedMeanings.size(); ++m) {
			Spannable meaning = parsedMeanings.get(m);
			if (m > 0)
				sb.append("\n");
				
			sb.append((m + 1) + ". ");
			sb.append(meaning);
		}
		return sb;
	}
	
	/**
	 * Formats a definition's meanings into a single string
	 * @param examples the examples
	 * @return the formatted string
	 */
	public static Spanned examples(List<Example> examples) {
		StringBuilder sb = new StringBuilder();
		for (int e = 0; e < examples.size(); ++e) {
			Example example = examples.get(e);
			sb.append(example.getUsage() + "<br /><i>" + example.getMeaning() + "</i>");
			if (e < examples.size() - 1)
				sb.append("<br /><br />");
		}
		return Html.fromHtml(sb.toString());
	}
}
