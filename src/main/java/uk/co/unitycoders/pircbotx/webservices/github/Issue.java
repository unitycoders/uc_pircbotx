package uk.co.unitycoders.pircbotx.webservices.github;

import java.util.Arrays;
import java.util.Collection;

public class Issue extends GithubObject {
	public int id;
	public String html_url;
	public int number;
	public String state;
	public String title;
	public String body;
	public User user;
	public Collection<Label> labels;
	public User assignee;
	public Milestone milestone;
	public int comments;
	public User closed_by;
	
	public Collection<GithubObject> getChildren() {
		GithubObject[] children = new GithubObject[]{
			user,
			assignee,
			milestone,
			closed_by
		};
		return Arrays.asList(children);
	}
	
	public String toString() {
		return "#"+number+": "+title;
	}
}