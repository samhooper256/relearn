/**
 * 
 */
package topics;

import base.problems.*;
import topics.settings.IntRange;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class Division extends AbstractTopic {

	private static final long serialVersionUID = 7496104666621702957L;

	public static final String NAME = "Division";
	public static final TopicFactory<Division> FACTORY = new TopicFactory<>(NAME, Division::new);
	
	private final IntRange divisor;
	
	public Division() {
		this(DEFAULT_COUNT);
	}
	
	public Division(int count) {
		super(count);
		divisor = new IntRange("Minimum Divisor", 1, 12);
		createSettings(divisor);
	}
	
	@Override
	public Problem generate() {
		int divisorValue = RNG.intInclusive(divisor);
		int dividend = divisorValue * RNG.intInclusive(1, 12);
		String expression = String.format("%d/%d", dividend, divisorValue);
		return MathProblem.builder().set(this, expression, MathAnswerMode.REAL_DECIMAL).build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
}
