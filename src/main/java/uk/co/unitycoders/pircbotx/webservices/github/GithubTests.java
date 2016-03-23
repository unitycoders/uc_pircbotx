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

public class GithubTests {

	public static void main(String[] args) throws IOException {
		testUser();

	}

	public static void testUser() throws IOException {
		GithubService service = new GithubService();

		Issue bot18 = service.getIssue("unitycoders", "uc_pircbotx", 18);
		System.out.println(bot18);

		Issue bot18_c = service.getIssue("unitycoders", "uc_pircbotx", 18);
		System.out.println(bot18_c);

		//User user = service.getUser("webpigeon");
		//System.out.println(user);

	}

}
