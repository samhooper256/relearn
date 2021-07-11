/**
 * 
 */
package math;

import java.math.*;

import utils.Parsing;

/**
 * <p>A real-valued fraction, represented as a non-negative numerator, a positive denominator, and a sign.</p>
 * @author Sam Hooper
 *
 */
public interface Fraction extends Complex {
	
	Fraction ZERO = FractionImpl.ZERO;
	char FRACTION_BAR_CHAR = '/';
	
	/** The numerator and denominator must be non-negative integers. A leading '+' or '-' is allowed.
	 * No leading or trailing whitespace is allowed.*/
	static boolean isValid(String str) {
		if(str.isEmpty())
			return false;
		int numStart = Parsing.isSign(str.charAt(0)) ? 1 : 0;
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		if(barIndex >= 0)
			return 	barIndex < str.length() - 1 && barIndex > numStart &&
					Parsing.containsOnlyDigits(str, numStart, barIndex) &&
					Parsing.containsOnlyDigits(str, barIndex + 1);
		else
			return Parsing.containsOnlyDigits(str, numStart);
	}
	
	static Fraction of(String str) {
		str = str.strip();
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		if(barIndex >= 0)
			return of(new BigInteger(str.substring(0, barIndex)), new BigInteger(str.substring(barIndex + 1)));
		else
			return of(new BigInteger(str), BigInteger.ONE);
	}
	static Fraction of(long numerator, long denominator) {
		return FractionImpl.ofImpl(numerator, denominator);
	}
	
	static Fraction of(BigInteger numerator, BigInteger denominator) {
		return FractionImpl.ofImpl(numerator, denominator);
	}
	
	/** Always non-negative.*/
	BigInteger numerator();
	
	/** Always non-negative.*/
	BigInteger denominator();
	
	boolean isNegative();
	
	/** A {@link Fraction} is proper iff {@link #numerator() numerator} is strictly less than
	 * {@link #denominator() denominator}.*/
	boolean isProper();
	
	boolean isImproper();
	
	/** Returns a {@link Fraction} with the numerator and denominator switched (and the sign preserved).
	 * @throws ArithmeticException if {@code this} {@link #isZero()}.*/
	Fraction reciprocal();
	
}