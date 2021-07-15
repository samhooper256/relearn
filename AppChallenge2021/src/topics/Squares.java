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
public class Squares extends AbstractTopic {

	private static final long serialVersionUID = 2052284801111791437L;
	
	public static final String NAME = "Squares";
	public static final TopicFactory<Squares> FACTORY = new TopicFactory<>(NAME, Squares::new);
	
	private final IntRange base;
	
	public Squares() {
		this(DEFAULT_COUNT);
	}
	
	public Squares(int count) {
		super(count);
		base = new IntRange("Base Value", 1, 12);
		createSettings(base);
	}
	
	@Override
	public Problem generate() {
		int baseValue = RNG.intInclusive(base);
		return MathProblem.builder()
				.set(this, String.format("%s^2", String.valueOf(baseValue)), MathAnswerMode.REAL_DECIMAL)
				.build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
}
