/**
 * 
 */
package math;

import static utils.Parsing.*;

import java.math.*;

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
		int numStart = isSign(str.charAt(0)) ? 1 : 0;
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		if(barIndex >= 0)
			return 	barIndex < str.length() - 1 && barIndex > numStart &&
					containsOnlyDigits(str, numStart, barIndex) &&
					containsOnlyDigits(str, barIndex + 1);
		else
			return containsOnlyDigits(str, numStart);
	}
	
	/** Returns {@code true} iff the given string represents a {@link #isValid(String) valid} {@link Fraction}
	 * <em>and</em> that fraction is in simplest form.*/
	static boolean isValidSimplified(String str) {
		if(str.isEmpty())
			return false;
		return isValidUnsignedSimplified(isSign(str.charAt(0)) ? str.substring(1) : str);
	}
	
	private static boolean isValidUnsignedSimplified(String str) {
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		if(barIndex == 0 || barIndex == str.length() - 1)
			return false;
		if(barIndex == -1)
			return containsOnlyDigits(str);
		else {
			String num = str.substring(0, barIndex), denom = str.substring(barIndex + 1);
			return 	containsOnlyDigits(num) && containsOnlyDigits(denom) &&
					isValidUnsignedSimplified(new BigInteger(num), new BigInteger(denom));
		}
	}
	
	private static boolean isValidUnsignedSimplified(BigInteger numerator, BigInteger denominator) {
		if(BigUtils.isNegative(numerator) || BigUtils.isNonPositive(denominator))
			return false;
		Fraction f = Fraction.of(numerator, denominator);
		return BigUtils.equals(f.numerator(), numerator) && BigUtils.equals(f.denominator(), denominator);
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
	
	/** Always positive.*/
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