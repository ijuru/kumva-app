package com.ijuru.kumva.search;

import java.util.List;

import com.ijuru.kumva.Definition;

public interface SearchListener {
	public void searchFinished(Search search, List<Definition> results);
}
