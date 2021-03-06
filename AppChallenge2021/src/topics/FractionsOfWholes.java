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
public class FractionsOfWholes extends AbstractTopic {

	private static final long serialVersionUID = 3689628391889552267L;
	
	public static final String NAME = "Fractions of Whole Numbers";
	public static final TopicFactory<FractionsOfWholes> FACTORY = new TopicFactory<>(NAME, FractionsOfWholes::new);
	
	private final IntSetting maxDenominator;
	
	public FractionsOfWholes() {
		this(DEFAULT_COUNT);
	} 
	
	public FractionsOfWholes(int count) {
		super(count);
		maxDenominator = new IntSetting("Maximum Denominator", 2, 12, 12);
		createSettings(maxDenominator);
	}
	
	@Override
	public Problem generate() { 
		int denominator = denominator();
		int numerator = numerator(denominator);
		Fraction fraction = Fraction.of(numerator, denominator);
		int number = number(denominator);
		Complex answer = fraction.multiply(Complex.of(number));
		return MathProblem.builder()
				.set(this, 
						Statement.builder()
						.addText("What is ")
						.addFraction(fraction)
						.addText(" of " )
						.addNumber(BigInteger.valueOf(number))
						.addText("?")
						.build(),
						answer,
						MathAnswerMode.REAL_FRACTION, MathAnswerMode.MIXED_NUMBER)
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
	
	public int number(int denom) 
	{
		int denominator = denom;
		ArrayList<Integer> dividends = new ArrayList<Integer>();
		int maxNumber = denominator * 12;
		
		for(int i = 1; i < maxNumber; i++)
		{
			if(i % denominator == 0)
			{
				dividends.add(i);
			}
		}
		int randomNum = RNG.intExclusive(0, dividends.size());
		return dividends.get(randomNum);
	}
}
