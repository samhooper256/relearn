/**
 * 
 */
package topics;

import base.problems.*;
import topics.settings.IntRange;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class DividingIntegers extends AbstractTopic {

	private static final long serialVersionUID = 7496104666621702957L;

	public static final String NAME = "Dividing Integers";
	public static final TopicFactory<DividingIntegers> FACTORY = new TopicFactory<>(NAME, DividingIntegers::new);
	
	private final IntRange divisor;
	
	public DividingIntegers() {
		this(DEFAULT_COUNT);
	}
	
	public DividingIntegers(int count) {
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
