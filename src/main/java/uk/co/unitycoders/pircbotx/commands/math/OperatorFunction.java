package uk.co.unitycoders.pircbotx.commands.math;

public class OperatorFunction implements Function {
	private String name;
	private boolean leftAssoc;
	private int precidence;
	
	public OperatorFunction(String name, boolean leftAssoc, int precidence) {
		this.name = name;
		this.leftAssoc = leftAssoc;
		this.precidence = precidence;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public double evaulate(double... args) {
		
		if ("+".equals(name)) {
			return args[0] + args[1];
		}
		
		if ("-".equals(name)) {
			return args[1] - args[0];
		}
		
		if ("/".equals(name)) {
			return args[1] / args[0];
		}
		
		if ("*".equals(name)) {
			return args[0] * args[1];
		}
		
		if ("%".equals(name)) {
			return args[1] % args[0];
		}
		
		if ("^".equals(name)) {
			return Math.pow(args[1], args[0]);
		}
		
		return 0;
	}

	@Override
	public boolean isLeftAssoc() {
		return leftAssoc;
	}

	@Override
	public int getPrecidence() {
		return precidence;
	}

}
