package uk.co.unitycoders.pircbotx.commands.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicMathTests {
	private static final Double ERROR_MARGIN = 0.00000000001; //something really small
	private MathCommand parser;
	
	@Before
	public void setup() {
		this.parser = new MathCommand();
	}
	
	
	@Test
	public void testTwoNumbersWorks() {
		
		double expected = 10;
		double result = parser.eval("5+5");
		
	 	Assert.assertEquals(expected, result, ERROR_MARGIN);
	}
	
	@Test
	public void testOneFuctionWorks() {
		
		double expected = Math.toRadians(90);
		double result = parser.eval("RAD(90)");
		
	 	Assert.assertEquals(expected, result, ERROR_MARGIN);
	}
	
	@Test
	public void testTwoFuctionsWorks() {
		
		double expected = Math.toRadians(90) * 2;
		double result = parser.eval("RAD(90)+RAD(90)");
		
	 	Assert.assertEquals(expected, result, ERROR_MARGIN);
	}
}
