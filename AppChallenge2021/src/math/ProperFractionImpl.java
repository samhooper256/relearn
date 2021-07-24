package math;

import java.math.BigInteger;

/**
 * 
 * @author Sam Hooper
 *
 */
final class ProperFractionImpl extends AbstractFraction implements ProperFraction {
	
	private static final ProperFractionImpl ZERO = new ProperFractionImpl(BigInteger.ZERO, BigInteger.ONE, false);
	
	static ProperFractionImpl zero() {
		assert ZERO != null;
		return ZERO;
	}
	ProperFractionImpl(BigInteger numerator, BigInteger denominator, boolean isNegative) {
		super(numerator, denominator, isNegative);
		assert 	BigUtils.isNonNegative(numerator) && BigUtils.isPositive(denominator) &&
				Fraction.isProper(numerator, denominator);
	}
	
}
