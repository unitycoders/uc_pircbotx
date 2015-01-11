package uk.co.unitycoders.pircbotx.webservices;

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
