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

package com.ijuru.kumva.test.ui;

import com.ijuru.kumva.ui.Format;
import com.ijuru.kumva.ui.QueryLinkSpan;

import android.test.AndroidTestCase;
import android.text.Spannable;

/**
 * Test case for Format class
 */
public class FormatTest extends AndroidTestCase {
	public void test_queryLink() {
		Spannable text = Format.queryLink(getContext(), "test");
		QueryLinkSpan[] spans = text.getSpans(0, text.length(), QueryLinkSpan.class);
		assertEquals("test", text.toString());
		assertEquals(1, spans.length);
		assertEquals("test", spans[0].getQuery());
	}
	
	public void test_parseQueryLinks() {
		assertNull(Format.parseQueryLinks(getContext(), null));
		
		Spannable empty = Format.parseQueryLinks(getContext(), "");
		QueryLinkSpan[] spans = empty.getSpans(0, empty.length(), QueryLinkSpan.class);
		assertEquals("", empty.toString());
		assertEquals(0, spans.length);
		
		Spannable text = Format.parseQueryLinks(getContext(), "abc {def} g {hij}");
		spans = text.getSpans(0, text.length(), QueryLinkSpan.class);
		assertEquals("abc def g hij", text.toString());
		assertEquals(2, spans.length);
		assertEquals("def", spans[0].getQuery());
		assertEquals("hij", spans[1].getQuery());
		
		Spannable broken = Format.parseQueryLinks(getContext(), "{abc} {def");
		spans = broken.getSpans(0, broken.length(), QueryLinkSpan.class);
		assertEquals("abc ", broken.toString());
		assertEquals(1, spans.length);
		assertEquals("abc", spans[0].getQuery());
	}
}
