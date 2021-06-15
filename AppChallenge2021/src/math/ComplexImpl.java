/**
 * 
 */
package math;

import java.math.*;

/**
 * <p>Implementation of {@link Complex}. Real and imaginary parts are represented with
 * {@link MathContext#DECIMAL32} precision and intermediate calculations are performed with
 * {@link MathContext#DECIMAL64} precision.</p>
 * @author Sam Hooper
 *
 */
record ComplexImpl(BigDecimal real, BigDecimal imaginary) implements Complex {
	
	private static final MathContext EXTERNAL_CONTEXT = MathContext.DECIMAL32;
	private static final MathContext INTERNAL_CONTEXT = MathContext.DECIMAL64;
	
	public ComplexImpl {
		real = real.round(EXTERNAL_CONTEXT);
		imaginary = imaginary.round(EXTERNAL_CONTEXT);
	}
	
	@Override
	public boolean isReal() {
		return BigUtils.isZero(imaginary());
	}

	@Override
	public boolean isImaginary() {
		return BigUtils.isZero(real());
	}

	@Override
	public Complex add(Complex c) {
		return Complex.of(
			real().add(c.real(), INTERNAL_CONTEXT),
			imaginary().add(c.imaginary(), INTERNAL_CONTEXT)
		);
	}

	@Override
	public Complex subtract(Complex c) {
		return add(c.negate());
	}
	
	@Override
	public Complex multiply(Complex other) {
		BigDecimal a = real(), b = imaginary();
		BigDecimal c = other.real(), d = other.imaginary();
		BigDecimal s1 = a.multiply(c, INTERNAL_CONTEXT);
		BigDecimal s2 = b.multiply(d, INTERNAL_CONTEXT);
		BigDecimal s3 = a.add(b, INTERNAL_CONTEXT).multiply(c.add(d, INTERNAL_CONTEXT));
		return Complex.of(
			s1.subtract(s2, INTERNAL_CONTEXT), s3.subtract(s1, INTERNAL_CONTEXT).subtract(s2, INTERNAL_CONTEXT)
		);
	}

	@Override
	public Complex divide(Complex denominator) {
		Complex conj = denominator.conjugate();
		BigDecimal div = denominator.abs2();
		Complex num = this.multiply(conj);
		return Complex.of(num.real().divide(div), num.imaginary().divide(div));
	}

	@Override
	public Complex negate() {
		return Complex.of(real().negate(INTERNAL_CONTEXT), imaginary().negate(INTERNAL_CONTEXT));
	}
	
	@Override
	public Complex conjugate() {
		if(isReal()) {
			return this;
		}
		return Complex.of(real(), imaginary().negate(INTERNAL_CONTEXT));
	}
	
	@Override
	public BigDecimal abs2() {
		return real().multiply(real(), INTERNAL_CONTEXT).add(imaginary().multiply(imaginary(), INTERNAL_CONTEXT), INTERNAL_CONTEXT);
	}
	
	@Override
	public BigDecimal abs() {
		return abs2().sqrt(INTERNAL_CONTEXT);
	}
	
	@Override
	public boolean isZero() {
		return BigUtils.isZero(real()) && BigUtils.isZero(imaginary());
	}

	@Override
	public String toString() {
		return String.format("%f+%fi", real(), imaginary());
	}

}
