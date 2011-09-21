package com.ijuru.kumva.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ijuru.kumva.Dictionary;
import com.ijuru.kumva.KumvaApplication;
import com.ijuru.kumva.R;
import com.ijuru.kumva.ui.DictionaryListAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

public class DictionariesActivity extends ListActivity {

	private DictionaryListAdapter adapter;
	
	/**
	 * Called when the activity is first created
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        this.adapter = new DictionaryListAdapter(this);
    	setListAdapter(adapter);
    	getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
    	/*getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dictionary dictionary = (Dictionary)parent.getItemAtPosition(position);				
				Intent intent = new Intent(getApplicationContext(), DictionaryPrefActivity.class);
				intent.putExtra("dict", dictionary.getName());
				startActivity(intent);
			}
		});*/
    	
    	KumvaApplication app = (KumvaApplication)getApplication();
    	
    	// Clear all items
    	adapter.clear();
    	
    	// Add existing dictionaries
    	for (Dictionary dict : app.getDictionaries())
    		adapter.add(dict);
    	
    	// Select active dictionary
    	int position = adapter.getPosition(app.getActiveDictionary());
    	if (position >= 0)
    		getListView().setItemChecked(position, true);
    }

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dictionaries, menu);
	    return true;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menudictadd:
			onMenuAdd();
		}
		return true;
	}

	/**
	 * Called when user selects add menu option
	 */
	private void onMenuAdd() {
		final EditText txtDictUrl = new EditText(this);
		new AlertDialog.Builder(this)
			.setTitle("New dictionary")
			.setView(txtDictUrl)
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
		     })
		     .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					String url = txtDictUrl.getText().toString();
					addDictionary(url);
				}
		     })
			.show();
	}
	
	/**
	 * Adds a dictionary by its URL
	 * @param url the URL of the dictionary site
	 */
	private void addDictionary(String url) {
		String siteInfoUrl = url + "/meta/site.xml.php";
		
		KumvaApplication app = (KumvaApplication)getApplication();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			URL dictUrl = new URL(siteInfoUrl);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(dictUrl.openStream());
			Element root =document.getDocumentElement();
			Node nameNode = root.getElementsByTagName("name").item(0);
			Node defLangNode = root.getElementsByTagName("definitionlang").item(0);
			Node meanLangNode = root.getElementsByTagName("meaninglang").item(0);
			
			String name = nameNode.getFirstChild().getNodeValue();
			String definitionLang = defLangNode.getFirstChild().getNodeValue();
			String meaningLang = meanLangNode.getFirstChild().getNodeValue();
			
			Dictionary dictionary = new Dictionary(url, name, definitionLang, meaningLang);
			
			adapter.add(dictionary);
			app.addDictionary(dictionary);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
