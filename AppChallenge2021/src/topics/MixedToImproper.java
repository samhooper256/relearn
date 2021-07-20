/**
 * 
 */
package topics;

import java.math.BigInteger;
import java.util.*;

import base.problems.*;
import math.*;
import topics.settings.IntSetting;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class MixedToImproper extends AbstractTopic {

	private static final long serialVersionUID = -4976775574415964442L;
	
	public static final String NAME = "Mixed Numbers to Improper Fractions";
	public static final TopicFactory<MixedToImproper> FACTORY = new TopicFactory<>(NAME, MixedToImproper::new);
	
	private final IntSetting maxDenominator;
	private final IntSetting maxWholeNumber;
	
	public MixedToImproper() {
		this(DEFAULT_COUNT);
	} 
	
	public MixedToImproper(int count) {
		super(count);
		maxDenominator = new IntSetting("Maximum Denominator", 2, 12, 12);
		maxWholeNumber = new IntSetting("Maximum Whole Number", 1, 12, 12);
		createSettings(maxDenominator, maxWholeNumber);
	}
	
	@Override
	public Problem generate() { 
		int denominator = denominator();
		int numerator = numerator(denominator);
		int wholeNumber = wholeNumber();
		Fraction fraction = Fraction.of(numerator, denominator);
		
		int finalNumerator = (denominator * wholeNumber) + numerator; 
		Complex answer = Fraction.of(finalNumerator, denominator);
		
		return MathProblem.builder()
				.set(this, 
						Statement.builder()
						.addText("Convert ")
						.addNumber(BigInteger.valueOf(wholeNumber))
						.addFraction(fraction)
						.addText(" to an improper fraction. ")
						.build(),
						answer,
						MathAnswerMode.REAL_FRACTION)
				.build();
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
	
	public int wholeNumber() 
	{
		int wholeNumber = RNG.intInclusive(1, maxDenominator.value());
		return wholeNumber;
	}
}
