/**
 * 
 */
package topics;

import java.util.List;

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
	private final List<TopicSetting> settings;
	
	public Division() {
		this(DEFAULT_COUNT);	}
	
	public Division(int count) {
		super(count);
		minDivisor = new IntSetting("Minimum Divisor", 1, 12, 1);
		maxDivisor = new IntSetting("Maximum Divisor", 1, 12, 12);
		settings = List.of(minDivisor, maxDivisor);
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

	@Override
	public List<TopicSetting> settings() {
		return settings;
	}
	
	public int createDivisor()
	{
		return RNG.intInclusive(minDivisor.value(), maxDivisor.value());
	}
}
