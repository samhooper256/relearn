package math;

import static math.BigUtils.isValidBigDecimal;

import java.math.*;

/**
 * <p>A real number consisting of a <em>non-zero</em> integer and a (possibly zero) proper fraction.</p>
 * @author Sam Hooper
 * 
 */
public interface MixedNumber extends Complex, FractionConvertible, MixedNumberConvertible {
	
	char SEPARATOR = ' ';
	
	static boolean isValid(String str) {
		int sepIndex = str.indexOf(SEPARATOR);
		if(sepIndex == -1)
			return isValidBigDecimal(str);
		return 	isValidBigDecimal(str.substring(0, sepIndex))
				&& ProperFraction.isValidUnsigned(str.substring(sepIndex + 1));
	}
	
	static MixedNumber of(String str) {
		assert isValid(str);
		int sepIndex = str.indexOf(SEPARATOR);
		if(sepIndex == -1) {
			BigInteger integer = new BigInteger(str);
			return of(integer, Complex.zero(), BigUtils.isNegative(integer));
		}
		BigInteger integer = new BigInteger(str.substring(0, sepIndex));
		ProperFraction fraction = ProperFraction.of(str.substring(sepIndex + 1));
		return of(integer, fraction, BigUtils.isNegative(integer));
	}
	
	static MixedNumber of(BigInteger integer, ProperFraction fraction, boolean isNegative) {
		return MixedNumberImpl.of(integer, fraction, isNegative);
	}
	
	static MixedNumber from(ImproperFraction im) {
		return MixedNumberImpl.from(im);
	}
	
	BigInteger integer();
	
	ProperFraction fraction();
	
	boolean isNegative();
	@Override
	MixedNumber negate();
	
	@Override
	default ImproperFraction toFraction() {
		return 	ImproperFraction.of
				(integer().multiply(fraction().denominator()).add(fraction().numerator()), fraction().denominator());
	}
	
	@Override
	default MixedNumber toMixedNumber() {
		return this;
	}
	
	@Override
	default BigDecimal real() {
		BigDecimal value = new BigDecimal(integer()).add(fraction().real());
		if(isNegative())
			value = value.negate();
		return value;
	}

	@Override
	default BigDecimal imaginary() {
		return BigDecimal.ZERO;
	}

	@Override
	default boolean isReal() {
		return true;
	}

	@Override
	default boolean isImaginary() {
		return isZero();
	}

	@Override
	default MixedNumber conjugate() {
		return this;
	}

	@Override
	default BigDecimal absAsBigDecimal() {
		return real();
	}

	/** Returns {@code false}, as a {@link MixedNumber} can never be zero.*/
	@Override
	default boolean isZero() {
		return false;
	}

	@Override
	default BigDecimal toBigDecimalExact() {
		return new BigDecimal(integer()).add(fraction().toBigDecimalExact());
	}

	@Override
	default int intValueExact() {
		if(!fraction().isZero())
			throw new ArithmeticException("This MixedNumber has fractional part");
		return integer().intValueExact();
	}
	
	@Override
	default String toParsableString() {
		return String.format("%d %s", integer(), fraction().toParsableString());
	}
	
}
