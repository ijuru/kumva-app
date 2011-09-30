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

package com.ijuru.kumva.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import com.ijuru.kumva.Example;
import com.ijuru.kumva.Meaning;
import com.ijuru.kumva.Tag;
import com.ijuru.kumva.activity.SearchActivity;

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
		spannable.setSpan(new ClickableSpan() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, SearchActivity.class);
				intent.putExtra("query", query);
				context.startActivity(intent);
			}}, 0, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannable;
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
	 * @return the formatted string
	 */
	public static Spannable meanings(Context context, List<Meaning> meanings) {
		StringBuilder sb = new StringBuilder();
		
		if (meanings.size() == 1)
			sb.append(meanings.get(0).getText());
		else {
			for (int m = 0; m < meanings.size(); ++m) {
				Meaning meaning = meanings.get(m);
				if (m > 0)
					sb.append("\n");
				
				sb.append((m + 1) + ". " + meaning.getText());
				
			}
		}
		return parseReferences(context, sb.toString());
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
	
	/**
	 * Parses references into clickable spans
	 * @param text
	 * @return
	 */
	public static Spannable parseReferences(final Context context, String text) {	
		if (text == null)
			return null;
		
		String clean = text.replace("{", "").replace("}", "");
		Spannable spanner = Spannable.Factory.getInstance().newSpannable(clean);
		
		int start = 0;
		for (int c = 0, cleanPos = 0; c < text.length(); c++) {
			if (text.charAt(c) == '{') {
				start = cleanPos;
			}
			else if (text.charAt(c) == '}') {
				final int end = cleanPos;
				final String ref = clean.substring(start, end);
				spanner.setSpan(new ClickableSpan() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(context, SearchActivity.class);
						intent.putExtra("query", ref);
						context.startActivity(intent);

					}}, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
			else
				++cleanPos;
		}
		
		return spanner;
	}	
}
