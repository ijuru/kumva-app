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

package com.ijuru.kumva.app.site;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Fetch task which writes a local file
 */
public class FetchToFileTask extends FetchTask<Boolean> {
	
	private File cacheFile;
	private int timeout;
	
	/**
	 * Constructs the task with a local file to write to
	 * @param cacheFile the local file
	 * @param timeout the timeout in milliseconds
	 */
	public FetchToFileTask(File cacheFile, int timeout) {
		this.cacheFile = cacheFile;
		this.timeout = timeout;
	}

	@Override
	protected Boolean fetch(String url) {
		try {
			// Connect to URL and set time outs
			URL audioURL = new URL(url);		
			URLConnection connection = audioURL.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			
			// Copy each byte from the remote file to the local file
			InputStream in = connection.getInputStream();
			OutputStream out = new FileOutputStream(cacheFile);
				
			int c = -1;
			while ((c = in.read()) != -1)
				out.write(c);
			
			in.close();
			out.close();
			
			return true;	
		} 
		catch (Exception e) {
			return false;
		}
	}
}
