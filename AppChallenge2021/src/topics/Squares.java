/**
 * 
 */
package topics;

import base.problems.*;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class Squares extends AbstractTopic {

	private static final long serialVersionUID = 2052284801111791437L;
	
	public static final String NAME = "Squares";
	public static final TopicFactory<Squares> FACTORY = new TopicFactory<>(NAME, Squares::new);
	
	private final IntRange number;
	
	public Squares() {
		this(DEFAULT_COUNT);
	}
	
	public Squares(int count) {
		super(count);
		number = new IntRange("Term Values", 1, 12, 1, 12);
		createSettings(number);
	}
	
	@Override
	public Problem generate() {
		int base = RNG.intInclusive(number.low(), number.high());
		return MathProblem.fromExpression(this, String.valueOf(base) + "^2");
	}
	
	@Override
	public String name() {
		return NAME;
	}
}
