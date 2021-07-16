/**
 * 
 */
package topics;

import base.problems.*;
import topics.settings.IntSetting;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class AddingFractions extends AbstractTopic {

	private static final long serialVersionUID = 8169117276659687049L;
	
	public static final String NAME = "Adding Fractions";
	public static final TopicFactory<AddingFractions> FACTORY = new TopicFactory<>(NAME, AddingFractions::new);
	
	private final IntSetting maxValue;
	
	public AddingFractions() {
		this(DEFAULT_COUNT);
	}
	
	public AddingFractions(int count) {
		super(count);
		maxValue = new IntSetting("Maximum Denominator", 2, 20, 6);
		createSettings(maxValue);
	}
	
	@Override
	public Problem generate() {
		int d1 = RNG.intInclusive(2, maxValue.value()), d2 = RNG.intInclusive(2, maxValue.value()),
			n1 = RNG.intExclusive(1, d1), n2 = RNG.intExclusive(1, d2);
		return 	MathProblem.builder().set(this, String.format("frac(%d,%d)+frac(%d,%d)", n1, d1, n2, d2),
				MathAnswerMode.REAL_SIMPLIFIED_FRACTION, MathAnswerMode.MIXED_NUMBER).build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
}
