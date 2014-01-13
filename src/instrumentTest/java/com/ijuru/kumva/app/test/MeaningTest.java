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

package com.ijuru.kumva.app.test;

import com.ijuru.kumva.app.Meaning;

import android.test.AndroidTestCase;

/**
 * Test case for Meaning class
 */
public class MeaningTest extends AndroidTestCase {
	public void test_parseFlags() {
		int flags0 = Meaning.parseFlags("");
		assertEquals(0, flags0);
		int flags1 = Meaning.parseFlags("rare");
		assertEquals(Meaning.FLAG_RARE, flags1);
		int flags2 = Meaning.parseFlags("rare, old");
		assertEquals(Meaning.FLAG_OLD|Meaning.FLAG_RARE, flags2);
	}
	
	public void test_makeFlagsCSV() {
		String str0 = Meaning.makeFlagsCSV(0);
		assertEquals("", str0);	
		String str1 = Meaning.makeFlagsCSV(Meaning.FLAG_OLD|Meaning.FLAG_RARE);
		assertEquals("old, rare", str1);
	}
}
