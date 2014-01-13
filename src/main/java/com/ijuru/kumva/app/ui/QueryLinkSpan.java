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

import com.ijuru.kumva.app.R;
import com.ijuru.kumva.app.activity.SearchActivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Span class for clickable text that generates search queries
 */
public class QueryLinkSpan extends ClickableSpan {

	private Context context;
	private String query;
	
	public QueryLinkSpan(Context context, String query) {
		this.context = context;
		this.query = query;
	}
	
	/**
	 * @see android.text.style.ClickableSpan#onClick(View)
	 */
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(context, SearchActivity.class);
		intent.putExtra("query", query);
		context.startActivity(intent);
	}
	
	/**
	 * @see android.text.style.ClickableSpan#updateDrawState(android.text.TextPaint)
	 */
	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setColor(context.getResources().getColor(R.color.lemma));
		ds.setUnderlineText(true);
	}

	/**
	 * Gets the query
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
}
