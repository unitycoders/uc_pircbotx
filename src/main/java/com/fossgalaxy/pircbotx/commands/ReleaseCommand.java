package com.fossgalaxy.pircbotx.commands;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.security.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command relating to release management.
 */
public class ReleaseCommand extends AnnotationModule {
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseCommand.class);

	private static final String GIT_FILENAME = "git.properties";
	private static final String GIT_ERROR = "Unable to get git data";
	
	private static final String PROP_COMMIT = "git.commit.id"; //last commit ID
	private static final String PROP_DESCRIBE = "git.commit.id.describe"; //repo state
	private static final String PROP_DIRTY = "git.dirty"; //was the repo dirty?
	private static final String PROP_VERSION = "git.build.version"; //the bot's version
	
	private final Properties properties;
	
	public ReleaseCommand() {
		super("release");
		properties = new Properties();
		loadGitInfo();
	}
	
	private void loadGitInfo() {
		try {
			InputStream loader = ReleaseCommand.class.getClassLoader().getResourceAsStream(GIT_FILENAME);
			if (loader == null) {
				return;
			}
			properties.load(loader);
			loader.close();
		} catch (IOException ex) {
			LOG.error("unable to load git properties", ex);
		}
	}
	
	private String getProp(String prop) {
		String answer = properties.getProperty(prop);
		if (answer == null) {
			throw new RuntimeException(GIT_ERROR);
		}
		return answer;
	}
	
	@Command("commit")
	@HelpText("print the last git commit before this was generated")
	public void getRelease(Message message) {
		String commit = getProp(PROP_COMMIT);
		String dirty = getProp(PROP_DIRTY);
		
		//let them know we don't match the commit exactly
		if (dirty.equals("true")) {
			commit = commit + " (with changes)";
		}
		
		message.respond(String.format("last commit: %s", commit));
	}
	
	@Command("version")
	@HelpText("print the git description from when this jar was generated")
	public void getDescribe(Message message) {
		String commit = getProp(PROP_DESCRIBE);
		message.respond(String.format("description: %s", commit));
	}
	
	@Command("version")
	@HelpText("print the version of the framework this bot was created from")
	public void getVersion(Message message) {
		String commit = getProp(PROP_VERSION);
		message.respond(String.format("version: %s", commit));
	}
	
	@Command("get")
	@HelpText("get a property based on it's ID")
	@Secured
	public void getProperty(Message message) {
		String property = properties.getProperty(message.getArgument(2, null));
		
		//TODO use usage when merged with master
		if (property == null) {
			message.respond("invalid usage");
			return;
		}
		
		message.respond(property);
	}
	
	@Command("list")
	@HelpText("get a property based on it's ID")
	@Secured
	public void listProperties(Message message) {
		Enumeration<Object> keys = properties.keys();
		
		StringBuilder b = new StringBuilder();
		while(keys.hasMoreElements()) {
			b.append(keys.nextElement());
			
			if (keys.hasMoreElements()) {
				b.append(", ");
			}
		}
		
		message.respond(b.toString());
	}
	
}
