package uk.co.unitycoders.pircbotx.webservices.github;


public class User extends GithubObject {
	public String login;
	public int id;
	public String html_url;
	public String name;
	public String company;
	public String blog;
	public String location;
	public String email;
	public boolean hireable;
	public String bio;
	public int public_repos;
	public int public_gists;
	public int followers;
	public int following;
	
	public String toString() {
		return name+" ("+login+")";
	}
}
