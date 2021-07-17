package math;

import static math.BigUtils.isValidBigDecimal;
import static math.BigUtils.isValidUnsignedBigDecimal;

import java.math.*;

import utils.*;

/** *
 * <p>A complex number.</p>
 * 
 * <p>All results are rounded using {@link MathContext#DECIMAL32}.</p>
 * @author Sam Hooper
 *
 */
public interface Complex {
	
	String IMAGINARY_UNIT = "i";
	char IMAGINARY_UNIT_CHAR = 'i';
	
	static ProperFraction zero() {
		return ProperFractionImpl.zero();
	}
	/** Returns {@code true} if the given {@code String} could be passed to {@link #of(String)} without throwing
	 * an exception. The given {@code String} must not contain any whitespace.*/
	static boolean isValid(String str) {
		return isValidInRectangularForm(str) || Fraction.isValid(str);
	}
	
	static boolean isValidInRectangularForm(String str) {
		if(str.endsWith(IMAGINARY_UNIT)) {
			int midSign = Strings.lastIndexOf(str, Parsing::isSign);
			return isValidBigDecimal(str, 0, midSign)
					&& isValidUnsignedBigDecimal(str, midSign + 1, str.length() - 1);
		}
		return isValidBigDecimal(str);
	}
	
	static boolean isImaginaryUnit(char c) {
		return c == IMAGINARY_UNIT_CHAR;
	}
	
	static Complex of(String str) { //TODO support MixedNumbers as well
		if(isValidBigDecimal(str))
			return Fraction.fromDecimal(str);
		if(Fraction.isValid(str))
			return Fraction.of(str);
		if(isValidInRectangularForm(str)) {
			if(str.endsWith(IMAGINARY_UNIT)) {
				int midSign = Strings.lastIndexOf(str, Parsing::isSign);
				return of(new BigDecimal(str.substring(0, midSign)),
						new BigDecimal(str.substring(midSign, str.length() - 1)));
			}
			return of(new BigDecimal(str));
		}
		throw new IllegalArgumentException(String.format("Invalid Complex literal: %s", str));
	}
	
	static Complex of(BigDecimal real) {
		return Fraction.fromDecimal(real);
	}
	
	static Complex of(BigDecimal real, BigDecimal imaginary) {
		if(BigUtils.isZero(imaginary))
			return of(real);
		return new ComplexImpl(real, imaginary);
	}
	
	static Complex of(long real, long imaginary) {
		if(imaginary == 0L)
			return of(real);
		return Complex.of(BigDecimal.valueOf(real), BigDecimal.valueOf(imaginary));
	}
	
	static Complex of(long real) {
		return Fraction.of(real);
	}
	
	/**
	 * Returns the real part of this {@link Complex} number, rounded to {@link MathContext#DECIMAL128}.
	 */
	BigDecimal real();
	
	/**
	 * Returns the real part of this {@link Complex} number, rounded to {@link MathContext#DECIMAL128}.
	 */
	BigDecimal imaginary();
	
	boolean isReal();
	
	boolean isImaginary();
	
	/** Should not be overridden. */
	default Complex add(Complex c) {
		return ComplexUtils.add(this, c);
	}
	
	/** Should not be overridden. */
	default Complex subtract(Complex c) {
		return ComplexUtils.subtract(this, c);
	}
	
	/** Should not be overridden.*/
	default Complex multiply(Complex c) {
		return ComplexUtils.multiply(this, c);
	}
	
	/** Should not be overridden.*/
	default Complex divide(Complex c) {
		return ComplexUtils.divide(this, c);
	}
	
	Complex negate();
	
	Complex conjugate();
	
	default Complex sqrt() {
		if(!isReal())
			throw new ArithmeticException("Cannot take the square root of a non-real number");
		return of(toBigDecimal(MathContext.DECIMAL128).sqrt(MathContext.DECIMAL128));
	}
	
	/** Returns the absolute value of this complex number, squared. This will always be a real number.
	 * This is equivalent to {@code this} times its {@link #conjugate()}.*/
	default BigDecimal abs2() {
		return absAsBigDecimal().pow(2);
	}
	
	default Complex abs() {
		return of(absAsBigDecimal());
	}
	
	/** Returns the absolute value of this {@link Complex} as a {@link BigDecimal}, rounded to
	 * {@link MathContext#DECIMAL128}.*/
	BigDecimal absAsBigDecimal();
	
	boolean isZero();
	
	/** Converts and the {@link #real() real} part of this {@link Complex} to a {@link BigDecimal} using the given
	 * {@link MathContext}. If this {@code Complex} has an {@link #imaginary() imaginary} component, it is silently
	 * ignored.*/
	default BigDecimal toBigDecimal(MathContext mc) {
		return real().round(mc);
	}
	
	BigDecimal toBigDecimalExact();
	
	int intValueExact();
	
	/** Returns {@code true} iff this {@link Complex} is exactly representable as a real terminating decimal.*/
	default boolean isExactlyRepresentable() {
		//TODO do properly:
		try {
			toBigDecimalExact();
		}
		catch(ArithmeticException e) {
			return false;
		}
		return true;
	}
	
	/** Returns a {@link Complex} with a a zero {@link #real() real} part and the same {@link #imaginary() imaginary}
	 * part as {@code this}.*/
	default Complex noReal() {
		if(isImaginary())
			return this;
		return of(BigDecimal.ZERO, imaginary());
	}
	
	/** Returns a String that can be parsed by a method of the implementing class or extending interface.*/
	String toParsableString();
	
}
