package uk.co.unitycoders.pircbotx.types;

public class Karma {
	private String target;
	private int karma;
	
	public Karma(String target) {
		this(target, 0);
	}
	
	public Karma(String target, int karma) {
		this.target = target;
		this.karma = karma;
	}
	
	public String getTarget() {
		return target;
	}
	
	public int getKarma() {
		return karma;
	}
	
	public String toString() {
		return target+" ("+karma+")";
	}

}
