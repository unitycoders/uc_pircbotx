package uk.co.unitycoders.pircbotx.security;

public class PermissionException extends RuntimeException {
	
	public PermissionException() {
		super("You don't have permission to do that");
	}

}
