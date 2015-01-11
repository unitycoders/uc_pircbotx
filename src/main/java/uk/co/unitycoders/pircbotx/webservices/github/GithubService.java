package uk.co.unitycoders.pircbotx.webservices.github;

import java.io.IOException;

import uk.co.unitycoders.pircbotx.webservices.CachingJsonService;

public class GithubService extends CachingJsonService<GithubObject> {
	private final static String API_BASE = "https://api.github.com";
	
	public User getUser(String username) throws IOException {
		return (User)makeRequest(API_BASE+"/users/"+username, User.class);
	}

	public Issue getIssue(String owner, String repo, int number) throws IOException {
		return (Issue)makeRequest(API_BASE+"/repos/"+owner+"/"+repo+"/issues/"+number, Issue.class);
	}
	
	/**
	 * Ensure we cache everything we see.
	 * 
	 * Github can return multiple objects per request, if we can extract these objects we can avoid
	 * making extra API requests, improving system performance.
	 */
	//XXX this has problems because github only returns partial definitions
	/*protected void notifyCaching(GithubObject object) {
		Collection<GithubObject> children = object.getChildren();
		for (GithubObject child : children) {
			if (child != null) {
				try{
					updateCache(child.getURL(), child);
					notifyCaching(child);
				}catch(MalformedURLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}*/
}