package math;

import static math.BigUtils.isValidBigDecimal;
import static math.BigUtils.isValidBigDecimalWithoutSign;

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
	
	Complex ONE = Complex.of("1");
	Complex HALF = Complex.of(".5");
	
	String IMAGINARY_UNIT = "i";
	char IMAGINARY_UNIT_CHAR = 'i';
	
	/** Returns {@code true} if the given {@code String} could be passed to {@link #of(String)} without throwing
	 * an exception. The given {@code String} must not contain any whitespace.*/
	static boolean isValid(String str) {
		return isValidInRectangularForm(str) || Fraction.isValid(str);
	}
	
	static boolean isValidInRectangularForm(String str) {
		if(str.endsWith(IMAGINARY_UNIT)) {
			int midSign = Strings.lastIndexOf(str, Parsing::isSign);
			return isValidBigDecimal(str, 0, midSign)
					&& isValidBigDecimalWithoutSign(str, midSign + 1, str.length() - 1);
		}
		return isValidBigDecimal(str);
	}
	
	static boolean isImaginaryUnit(char c) {
		return c == IMAGINARY_UNIT_CHAR;
	}
	
	static Complex of(String str) {
		if(isValidInRectangularForm(str)) {
			if(str.endsWith(IMAGINARY_UNIT)) {
				int midSign = Strings.lastIndexOf(str, Parsing::isSign);
				return of(new BigDecimal(str.substring(0, midSign)),
						new BigDecimal(str.substring(midSign, str.length() - 1)));
			}
			return of(new BigDecimal(str));
		}
		else if(Fraction.isValid(str)) {
			return Fraction.of(str);
		}
		throw new IllegalArgumentException(String.format("Invalid Complex literal: %s", str));
	}
	
	static Complex of(BigDecimal real) {
		return new ComplexImpl(real, BigDecimal.ZERO);
	}
	
	static Complex of(BigDecimal real, BigDecimal imaginary) {
		return new ComplexImpl(real, imaginary);
	}
	
	static Complex of(long real, long imaginary) {
		return Complex.of(BigDecimal.valueOf(real), BigDecimal.valueOf(imaginary));
	}
	
	static Complex of(long real) {
		return Complex.of(BigDecimal.valueOf(real), BigDecimal.ZERO);
	}
	
	BigDecimal real();
	
	BigDecimal imaginary();
	
	boolean isReal();
	
	boolean isImaginary();
	
	Complex add(Complex c);
	
	Complex subtract(Complex c);
	
	Complex multiply(Complex c);
	
	Complex divide(Complex c);
	
	Complex negate();
	
	Complex conjugate();
	
	default Complex sqrt() {
		if(!isReal())
			throw new ArithmeticException("Cannot take the square root of a non-real number");
		return of(toBigDecimal(MathContext.DECIMAL64).sqrt(MathContext.DECIMAL64));
	}
	
	/** Returns the absolute value of this complex number, squared. This will always be a real number.
	 * This is equivalent to {@code this} times its {@link #conjugate()}.*/
	default BigDecimal abs2() {
		return absAsBigDecimal().pow(2);
	}
	
	default Complex abs() {
		return of(absAsBigDecimal());
	}
	
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
}
