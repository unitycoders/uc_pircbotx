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

	@Override
	public String toString() {
		return name+" ("+login+")";
	}
}
