/**
 * 
 */
package topics;

import base.problems.*;
import math.Complex;
import math.Fraction;
import topics.settings.IntSetting;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class DividingFractions extends AbstractTopic {

	private static final long serialVersionUID = 1325236424274853297L;

	public static final String NAME = "Dividing Fractions";
	public static final TopicFactory<DividingFractions> FACTORY = new TopicFactory<>(NAME, DividingFractions::new);
	
	private final IntSetting maxDenominator;
	
	public DividingFractions() {
		this(DEFAULT_COUNT);
	}
	
	public DividingFractions(int count) {
		super(count);
		maxDenominator = new IntSetting("Maximum Denominator", 2, 20, 12);
		createSettings(maxDenominator);
	}
	
	@Override
	public Problem generate() {
		int denominator1 = denominator();
		int numerator1 = numerator(denominator1);
		int denominator2 = denominator();
		int numerator2 = numerator(denominator2);
		
		return 	MathProblem.builder().set(this, String.format("frac(%d,%d)/(frac(%d,%d)", 
				numerator1, denominator1, numerator2, denominator2),
				MathAnswerMode.INTEGER, MathAnswerMode.REAL_SIMPLIFIED_FRACTION, MathAnswerMode.MIXED_NUMBER).build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public int numerator(int denom)
	{
		int denominator = denom;
		int numerator = RNG.intExclusive(1, denominator);
		return numerator;
	}
	
	public int denominator()
	{
		int denominator = RNG.intInclusive(2, maxDenominator.value());
		return denominator;
	}
	
}
