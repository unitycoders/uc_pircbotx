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
