package com.ijuru.kumva.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ijuru.kumva.Definition;
import com.ijuru.kumva.Meaning;

/**
 * SAX handler for Kumva query XML
 */
public class QueryXMLHandler extends DefaultHandler {
	private Definition curDefinition;
	private Meaning curMeaning;
	private String elementName = "";
	private List<DefinitionListener> listeners = new ArrayList<DefinitionListener>();
		
	public void addListener(DefinitionListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("definition")) {
			curDefinition = new Definition();
			curDefinition.setWordClass(attributes.getValue("wordclass"));
		}
		else if (localName.equals("meaning")) {
			curMeaning = new Meaning();
			//curMeaning.setFlags();
		}
		
		elementName = localName;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String val = new String(ch, start, length);
		
		if (elementName.equals("prefix"))
			curDefinition.setPrefix(val);
		else if (elementName.equals("lemma"))
			curDefinition.setLemma(val);
		else if (elementName.equals("modifier"))
			curDefinition.setModifier(val);
		else if (elementName.equals("meaning"))
			curMeaning.setText(val);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (localName.equals("definition")) {
			for (DefinitionListener listener : listeners)
				listener.found(curDefinition);
		}
		if (localName.equals("meaning")) {
			curDefinition.addMeaning(curMeaning);
		}

		super.endElement(uri, localName, qName);
	}

	
}
