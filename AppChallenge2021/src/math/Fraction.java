/**
 * 
 */
package math;

import java.math.*;

/**
 * <p>A real-valued fraction, represented as a non-negative numerator, a positive denominator, and a sign.</p>
 * @author Sam Hooper
 *
 */
public interface Fraction extends Complex {
	
	Fraction ZERO = FractionImpl.ZERO;
	
	/** Always non-negative.*/
	BigInteger numerator();
	
	/** Always non-negative.*/
	BigInteger denominator();
	
	boolean isNegative();
	
	/** A {@link Fraction} is proper iff {@link #numerator() numerator} is strictly less than
	 * {@link #denominator() denominator}.*/
	boolean isProper();
	
	boolean isImproper();
	
	/** Converts this {@link Fraction} to a {@link BigDecimal} using the {@link MathContext#DECIMAL32} context. */
	default BigDecimal toBigDecimal() {
		return toBigDecimal(MathContext.DECIMAL32);
	}
	
	/** Converts this {@link Fraction} to a {@link BigDecimal} using the given {@link MathContext}. */
	BigDecimal toBigDecimal(MathContext mc);
	
	/** Returns a {@link Fraction} with the numerator and denominator switched (and the sign preserved).
	 * @throws ArithmeticException if {@code this} {@link #isZero()}.*/
	Fraction reciprocal();
	
}