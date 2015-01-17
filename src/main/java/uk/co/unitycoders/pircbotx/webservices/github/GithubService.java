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