package math;

import java.math.BigInteger;

/**
 * <p>A class representing an improper fraction - specifically, any fraction <em>x</em> such that {@code |x| >= 1}.</p>
 * @author Sam Hooper
 *
 */
public class ImproperFractionImpl extends AbstractFraction implements ImproperFraction {
	
	ImproperFractionImpl(BigInteger numerator, BigInteger denominator, boolean isNegative) {
		super(numerator, denominator, isNegative);
		assert 	BigUtils.isNonNegative(numerator) && BigUtils.isPositive(denominator) &&
				Fraction.isImproper(numerator, denominator);
	}
	
}
