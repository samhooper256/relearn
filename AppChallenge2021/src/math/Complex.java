package math;

import java.math.BigDecimal;
/** *
 * <p>A complex number.</p>
 * @author Sam Hooper
 *
 */
public interface Complex {
	
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
	BigDecimal abs2();
	
	BigDecimal abs();
	
}
