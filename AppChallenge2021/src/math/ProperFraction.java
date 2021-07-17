package math;

import static utils.Parsing.*;

import java.math.BigInteger;

public interface ProperFraction extends Fraction {
	
	static boolean isValid(String str) {
		if(str.isEmpty())
			return false;
		return isSign(str.charAt(0)) ? isValidUnsigned(str.substring(1)) : isValidUnsigned(str);
	}
	
	static boolean isValidUnsigned(String str) {
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		if(barIndex <= 0 || barIndex == str.length() - 1)
			return false;
		String numStr = str.substring(0, barIndex);
		String denomStr = str.substring(barIndex + 1);
		if(!containsOnlyDigits(numStr) || !containsOnlyDigits(denomStr))
			return false;
		BigInteger num = new BigInteger(numStr), denom = new BigInteger(denomStr);
		return Fraction.isProper(num, denom);
	}
	
	static boolean isValidSimplified(String str) {
		return	ProperFraction.isValid(str) && Fraction.isValidSimplified(str);
	}
	
	/** {@code str} is assumed to be {@link #isValid(String) valid}.*/
	static ProperFraction of(String str) {
		assert isValid(str);
		int barIndex = str.indexOf(FRACTION_BAR_CHAR);
		BigInteger 	num = new BigInteger(str.substring(0, barIndex)),
					denom = new BigInteger(str.substring(barIndex + 1));
		return of(num, denom);
	}
	
	static ProperFraction of(long numerator, long denominator) {
		return of(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
	}
	
	static ProperFraction of(BigInteger numerator, BigInteger denominator) {
		Fraction f = Fraction.of(numerator, denominator);
		if(!f.isProper())
			throw new IllegalArgumentException("not proper");
		return (ProperFraction) f;
	}
	
	@Override
	default ProperFraction abs() {
		return (ProperFraction) Fraction.super.abs();
	}
	
	@Override
	default boolean isProper() {
		return true;
	}
	
}
