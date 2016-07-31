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
package com.fossgalaxy.pircbotx.webservices.github;

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

	@Override
	public Collection<GithubObject> getChildren() {
		GithubObject[] children = new GithubObject[]{
				user,
				assignee,
				milestone,
				closed_by
		};
		return Arrays.asList(children);
	}

	@Override
	public String toString() {
		return "#"+number+": "+title;
	}
}