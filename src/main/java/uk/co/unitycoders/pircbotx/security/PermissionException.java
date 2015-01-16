package uk.co.unitycoders.pircbotx.security;

public class PermissionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PermissionException() {
		super("You don't have permission to do that");
	}

}
