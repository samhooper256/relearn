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
public class ImproperToMixed extends AbstractTopic {

	private static final long serialVersionUID = 6620525095602391884L;
	
	public static final String NAME = "Improper Fractions to Mixed Numbers";
	public static final TopicFactory<ImproperToMixed> FACTORY = new TopicFactory<>(NAME, ImproperToMixed::new);
	
	private final IntSetting maxDenominator;
	
	public ImproperToMixed() {
		this(DEFAULT_COUNT);
	} 
	
	public ImproperToMixed(int count) {
		super(count);
		maxDenominator = new IntSetting("Maximum Denominator", 2, 12, 12);
		createSettings(maxDenominator);
	}
	
	@Override
	public Problem generate() { 
		int denominator = denominator();
		int numerator = numerator(denominator);
		Fraction fraction = Fraction.of(numerator, denominator);
		
		int answerWholeNumber = numerator/denominator;
		int answerNumerator = numerator - (answerWholeNumber * denominator);
		Complex answerFraction = Fraction.of(answerNumerator, denominator);
		Complex answer = Complex.of(answerWholeNumber).add(answerFraction);
		
		return MathProblem.builder()
				.set(this, 
						Statement.builder()
						.addText("Convert ")
						.addFraction(fraction)
						.addText(" to a mixed number. ")
						.build(),
						answer,
						MathAnswerMode.MIXED_NUMBER)
				.build();
	}
	
	@Override
	public String name() {
		return NAME;
	} 
	
	
	public int numerator(int denom)
	{
		int denominator = denom;
		int minNumerator = denominator + 1;
		int maxNumerator = denominator * 12;
		int numerator = RNG.intExclusive(minNumerator, maxNumerator);
		return numerator;
	}
	
	public int denominator()
	{
		int denominator = RNG.intInclusive(2, maxDenominator.value());
		return denominator;
	}
}
