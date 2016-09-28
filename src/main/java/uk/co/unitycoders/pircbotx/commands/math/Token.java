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
	
	@Override
	public boolean equals(Object object) {
		try {
			Token other = (Token)object;
			if (!type.equals(other.type)){
				return false;
			}
			
			//This might not work if other.value != null && value == null
			//that shouldn't happen
			return value != null && value.equals(other.value);
		} catch (ClassCastException ex) {
			return false;
		}
	}

	public TokenType getType() {
		return type;
	}

}
