/**
 * Copyright © 2015 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.webservices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import com.google.gson.Gson;

/**
 * Class for dealing with JSON based web services.
 *
 */
public class JsonService <T> {

	public T makeRequest(String url, Class<? extends T> responseClass) throws IOException {
		return makeRequest(new URL(url), responseClass);
	}

	public T makeRequest(URL url, Class<? extends T> responseClass) throws IOException {
		try (
				InputStream stream = url.openStream();
				Reader reader = new InputStreamReader(stream);
				){
			Gson gson = new Gson();
			return gson.fromJson(reader, responseClass);
		}
	}
}
