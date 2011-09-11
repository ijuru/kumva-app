package com.ijuru.kumva.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Example;
import com.ijuru.kumva.Meaning;

/**
 * SAX handler for Kumva query XML
 */
public class QueryXMLHandler extends DefaultHandler {
	private Definition curDefinition;
	private Meaning curMeaning;
	private Example curExample;
	private boolean inMeanings = false;
	private StringBuilder elementText = null;
	private List<DefinitionListener> listeners = new ArrayList<DefinitionListener>();

	/**
	 * Adds the definition listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(DefinitionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("definition")) {
			Log.d("Kumva", "definition start");
			curDefinition = new Definition();
			curDefinition.setWordClass(attributes.getValue("wordclass"));
		} else if (localName.equals("meanings"))
			inMeanings = true;
		else if (localName.equals("meaning")) {
			curMeaning = new Meaning();
			// curMeaning.setFlags();
		} else if (localName.equals("example")) {
			curExample = new Example();
		}

		elementText = new StringBuilder();
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		elementText.append(new String(ch, start, length));
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (localName.equals("definition")) {
			Log.d("Kumva", "definition end: " + curDefinition.getPrefix()
					+ curDefinition.getLemma());
			definitionFinished();
		} else if (localName.equals("meanings"))
			inMeanings = false;
		else if (localName.equals("meaning")) {
			if (inMeanings)
				curDefinition.addMeaning(curMeaning);
		} else if (localName.equals("example")) {
			curDefinition.addExample(curExample);
		}
		
		// Get the complete text inside this element
		String text = elementText.toString();

		// Read text-only elements
		if (localName.equals("prefix"))
			curDefinition.setPrefix(text);
		else if (localName.equals("lemma"))
			curDefinition.setLemma(text);
		else if (localName.equals("modifier"))
			curDefinition.setModifier(text);
		else if (localName.equals("pronunciation"))
			curDefinition.setPronunciation(text);
		else if (localName.equals("meaning")) {
			if (inMeanings)
				curMeaning.setText(text);
			else
				curExample.setMeaning(text);
		} else if (localName.equals("comment"))
			curDefinition.setComment(text);
	}

	/**
	 * Notifies all listeners that the current definition is ready
	 */
	private void definitionFinished() {
		// Notify listeners
		for (DefinitionListener listener : listeners)
			listener.found(curDefinition);
	}
}
