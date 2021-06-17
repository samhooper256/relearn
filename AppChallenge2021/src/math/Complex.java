package math;

import java.math.*;
/** *
 * <p>A complex number.</p>
 * @author Sam Hooper
 *
 */
public interface Complex {
	
	/** Returns {@code true} if the given {@code String} could be passed to {@link #of(String)} without throwing
	 * an exception.*/
	static boolean isValid(String num) {
		return true; //TODO
	}
	
	static Complex of(String num) {
		if(num.contains("i"))
			throw new UnsupportedOperationException("Parsing non-reals from Strings is unfinished"); //TODO
		if(!isValid(num))
			throw new IllegalArgumentException(String.format("Invalid Complex literal: %s", num));
		return of(new BigDecimal(num));
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
}
