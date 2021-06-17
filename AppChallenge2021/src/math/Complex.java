package math;

import static math.BigUtils.isValidBigDecimal;
import static math.BigUtils.isValidBigDecimalWithoutSign;

import java.math.*;

import utils.*;

/** *
 * <p>A complex number.</p>
 * @author Sam Hooper
 *
 */
public interface Complex {
	
	String IMAGINARY_UNIT = "i";
	char IMAGINARY_UNIT_CHAR = 'i';
	
	/** Returns {@code true} if the given {@code String} could be passed to {@link #of(String)} without throwing
	 * an exception. The given {@code String} must not contain any whitespace.*/
	static boolean isValid(String str) {
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
		if(!isValid(str))
			throw new IllegalArgumentException(String.format("Invalid Complex literal: %s", str));
		if(str.endsWith(IMAGINARY_UNIT)) {
			int midSign = Strings.lastIndexOf(str, Parsing::isSign);
			return of(new BigDecimal(str.substring(0, midSign)),
					new BigDecimal(str.substring(midSign, str.length() - 1)));
		}
		return of(new BigDecimal(str));
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
	
	/** Returns the absolute value of this complex number, squared. This will always be a real number.
	 * This is equivalent to {@code this} times its {@link #conjugate()}.*/
	default BigDecimal abs2() {
		return abs().pow(2);
	}
	
	BigDecimal abs();
	
	boolean isZero();
	
	BigDecimal toBigDecimalExact();
	
	int intValueExact();
	
}
