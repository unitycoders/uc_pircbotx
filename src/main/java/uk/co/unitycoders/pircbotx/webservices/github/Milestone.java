package uk.co.unitycoders.pircbotx.webservices.github;

public class Milestone extends GithubObject {
	public int number;
	public String state;
	public String title;
	public String description;
	public User creator;
	public int open_issues;
	public int closed_issues;
}
