/**
 * 
 */
package topics;

import base.*;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class Division extends AbstractTopic {

	private static final long serialVersionUID = 7496104666621702957L;

	public static final String NAME = "Division";
	public static final TopicFactory<Division> FACTORY = new TopicFactory<>(NAME, Division::new);
	
	private final IntSetting minDivisor;
	private final IntSetting maxDivisor;
	
	public Division() {
		this(DEFAULT_COUNT);
	}
	
	public Division(int count) {
		super(count);
		minDivisor = new IntSetting("Minimum Divisor", 1, 12, 1);
		maxDivisor = new IntSetting("Maximum Divisor", 1, 12, 12);
		createSettings(minDivisor, maxDivisor);
	}
	
	@Override
	public Problem generate() {
		int divisor = createDivisor();
		int quotient = divisor * RNG.intInclusive(1, 12);
		return MathProblem.fromExpression(this, String.format("%d/%d", quotient, divisor));
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public int createDivisor() {
		return RNG.intInclusive(minDivisor.value(), maxDivisor.value());
	}
	
}