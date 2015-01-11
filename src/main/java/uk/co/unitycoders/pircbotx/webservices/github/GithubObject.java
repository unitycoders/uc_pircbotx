package uk.co.unitycoders.pircbotx.webservices.github;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

public class GithubObject {
	public String url;
	
	public Collection<GithubObject> getChildren() {
		return Collections.emptyList();
	}

	public URL getURL() throws MalformedURLException {
		// TODO Auto-generated method stub
		return new URL(url);
	}

}
