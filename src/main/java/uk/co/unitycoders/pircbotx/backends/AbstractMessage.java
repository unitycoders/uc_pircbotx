package uk.co.unitycoders.pircbotx.backends;

import java.util.Iterator;
import java.util.List;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * Things common to messages regardless of the backend.
 */
public abstract class AbstractMessage implements Message {
	private final List<String> args;
	
	public AbstractMessage(List<String> args) {
		this.args = args;
	}

	@Override
	public String getMessage() {
		if (args.size() <= 2) {
			return "";
		}

		// get the argument list
		List<String> cmdArgs = args.subList(2, args.size());

		//emulate String.join in java 1.7
		StringBuilder argStr = new StringBuilder();
		Iterator<String> argItr = cmdArgs.iterator();
		while(argItr.hasNext()) {
			argStr.append(argItr.next());
			if (argItr.hasNext()) {
				argStr.append(" ");
			}
		}

		return argStr.toString();
	}

	@Override
	public  String getRawMessage() {
		return getMessage();
	}

	@Override
	public String getArgument(int id, String defaultValue) {
		if (args == null || args.size() <= id){
			return defaultValue;
		}

		return args.get(id);
	}

	@Override
	public String getArgument(int id) {
		return args.get(id);
	}

	@Override
	public void insertArgument(int i, String arg) {
		args.add(i, arg);	
	}
	
	@Override
	public void respondSuccess() {
		respond("The operation was successful.");
	}
	
}
