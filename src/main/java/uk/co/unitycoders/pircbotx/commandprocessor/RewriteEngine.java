package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.Map;
import java.util.TreeMap;

public class RewriteEngine {
	private Map<String, String> rules;
	
	public RewriteEngine() {
		this.rules = new TreeMap<String, String>();
	}
	
	public void addRule(String regex, String replace) {
		rules.put(regex, replace);
	}
	
	public String process(String source) {
		
		String output = source;
		for (Map.Entry<String, String> rule : rules.entrySet()) {
			output = output.replaceAll(rule.getKey(), rule.getValue());
		}
		
		return output;
	}

}
