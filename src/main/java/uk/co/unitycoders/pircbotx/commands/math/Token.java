package uk.co.unitycoders.pircbotx.commands.math;

public class Token {
	private TokenType type;
	private String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public Token(TokenType type) {
		this.type = type;
		this.value = null;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isType(TokenType type) {
		return this.type == type;
	}
	
	public String toString() {
		return type+" "+value;
	}

}
