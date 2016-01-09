package uk.co.unitycoders.pircbotx.commands;

import java.util.Random;

import org.pircbotx.Channel;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

/**
 * 
 * 8ball algorithm and responses stolen from supybot who stole
 * it from mozbot.
 */
public class GamesCommand extends AnnotationModule {
	private final String[] positive = {
			"It is possible", "Yes!", "Of course.",
			"Naturally.", "Obviously.", "It shall be.",
			"The outlook is good", "It is so.",
			"One would be wise to think so.",
			"The answer is certainly yes."
	};
	private final String[] negative = {
			"In your dreams.", "I doubt it very much.",
			"No chance.", "The outlook is poor.",
			"Unlikely.", "About as likely as pigs flying.",
			"You're kdding, right?", "NO!", "NO.", "No.",
			"The answer is a resounding no."
	};
	private final String[] unknown = {
			"Maybe...", "No clue.", "_I_ don\'t know.",
            "The outlook is hazy, please ask again later.",
            "What are you asking me for?", "Come again?",
            "You know the answer better than I.",
            "The answer is def-- oooh! shiny thing!"
	};
	
	private Random random;
	private int rouletteBullet = -1;
	private int rouletteChamber = -1;

	public GamesCommand() {
		super("games");
		this.random = new Random();
	}
	
	@Command("coin")
	public void onCoin(Message message) {
		String trueMessage = message.getArgument(2, "heads");
		String falseMessage = message.getArgument(3, "tails");
		String option = random.nextBoolean() ? trueMessage:falseMessage;
		message.respond(option);
	}
	
	@Command("dice")
	public void onDice(Message message) {
		try {
			int sides = Integer.parseInt(message.getArgument(2, "6"));
			int roll = random.nextInt(sides);
			message.respond(String.format("I rolled %d", roll));
		} catch (IllegalArgumentException ex) {
			message.respond("Usage: games dice [sides]");
		}
	}
	
	@Command("8ball")
	public void on8Ball(Message message) {
		String question = message.getArgument(2, null);
		
		//consult the ball
		Boolean result;
		if (question != null) {
			result = checkTheBall(question.length());
		} else {
			result = checkTheBall(random.nextInt(3));
		}
		
		//figure out what set we should use
		String[] responses;
		if (result == null) {
			responses = unknown;
		} else if (result) {
			responses = positive;
		} else {
			responses = negative;
		}
		
		String response = responses[random.nextInt(responses.length)];
		message.respond(response);
	}
	
	private Boolean checkTheBall(int length) {
		if (length % 3 == 0) {
			return true;
		} else if (length % 3 == 1) {
			return false;
		} else {
			return null;
		}
	}
	
	@Command("roulette")
	public void onRoulette(Message message) {
		String spin = message.getArgument(2, null);
		if (spin != null) {
			this.rouletteBullet = random.nextInt(6);
			message.respond("*SPIN* Are you feeling lucky?");
			return;
		}
		
		if (rouletteBullet == rouletteChamber) {
			this.rouletteBullet = random.nextInt(6);
			this.rouletteChamber = random.nextInt(6);
			
			String channelName = message.getTargetName();
			Channel channel = message.getBot().getUserChannelDao().getChannel(channelName);
			if (channel.isOp(message.getBot().getUserBot())){
				channel.send().kick(message.getUser(), "BANG!");
			} else {
				message.respond("*BANG* Hey, who put a blank in here?!");
			}
			message.sendAction("reloads and spins the chambers");
		} else {
			message.respond("*click*");
			this.rouletteChamber += 1;
			this.rouletteChamber %= 6;
		}
	}
	
	

}
