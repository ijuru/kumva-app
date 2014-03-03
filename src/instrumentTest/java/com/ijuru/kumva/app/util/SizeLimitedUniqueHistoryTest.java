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

package com.ijuru.kumva.app.util;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Tests for {@link SizeLimitedUniqueHistory}
 */
public class SizeLimitedUniqueHistoryTest extends AndroidTestCase {

	public void test_integration() {

		SizeLimitedUniqueHistory<String> history = new SizeLimitedUniqueHistory<String>(3);
		history.add("first");

		Assert.assertEquals(1, history.size());

		history.add("second");
		history.add("third");

		Assert.assertEquals(3, history.size());

		history.add("fourth");

		Assert.assertEquals(3, history.size());
		Assert.assertEquals("fourth", history.get(0));
		Assert.assertEquals("third", history.get(1));
		Assert.assertEquals("second", history.get(2));

		// Re-add "third" and check that it's now first and no duplicated
		history.add("third");

		Assert.assertEquals(3, history.size());
		Assert.assertEquals("third", history.get(0));
		Assert.assertEquals("fourth", history.get(1));
		Assert.assertEquals("second", history.get(2));
	}
}