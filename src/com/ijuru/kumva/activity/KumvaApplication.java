package com.ijuru.kumva.activity;

import com.ijuru.kumva.Definition;

import android.app.Application;

public class KumvaApplication extends Application {
	
	private Definition definition;

	/**
	 * @return the definition
	 */
	public Definition getDefinition() {
		return definition;
	}

	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
}
