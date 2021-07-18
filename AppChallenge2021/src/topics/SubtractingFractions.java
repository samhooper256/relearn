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
public class SubtractingFractions extends AbstractTopic {

	private static final long serialVersionUID = -8915556384262314531L;
	
	public static final String NAME = "Subtracting Fractions";
	public static final TopicFactory<SubtractingFractions> FACTORY = new TopicFactory<>(NAME, SubtractingFractions::new);
	
	private final IntSetting maxDenominator;
	
	public SubtractingFractions() {
		this(DEFAULT_COUNT);
	}
	
	public SubtractingFractions(int count) {
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
		
		//No negative answers allowed.
		while(((numerator1/denominator1) - (numerator2/denominator2)) < 0)
		{
			denominator1 = denominator();
			numerator1 = numerator(denominator1);
		}
		
		return 	MathProblem.builder().set(this, String.format("frac(%d,%d)-frac(%d,%d)", 
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
