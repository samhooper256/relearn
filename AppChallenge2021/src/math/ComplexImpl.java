/**
 * 
 */
package math;

import java.math.*;
import java.util.Objects;

/**
 * <p>Implementation of {@link Complex}. All calculations are carried out with {@link MathContext#DECIMAL128}
 * precision.</p>
 * @author Sam Hooper
 *
 */
class ComplexImpl implements Complex {
	
	private static final MathContext CONTEXT = MathContext.DECIMAL128;
	
	final BigDecimal real, imaginary;
	
	public ComplexImpl(BigDecimal real, BigDecimal imaginary) {
		this.real = real.round(CONTEXT);
		this.imaginary = imaginary.round(CONTEXT);
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
			real().add(c.real(), CONTEXT),
			imaginary().add(c.imaginary(), CONTEXT)
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
		BigDecimal s1 = a.multiply(c, CONTEXT);
		BigDecimal s2 = b.multiply(d, CONTEXT);
		BigDecimal s3 = a.add(b, CONTEXT).multiply(c.add(d, CONTEXT));
		return Complex.of(
			s1.subtract(s2, CONTEXT), s3.subtract(s1, CONTEXT).subtract(s2, CONTEXT)
		);
	}

	@Override
	public Complex divide(Complex denominator) {
		Complex conj = denominator.conjugate();
		BigDecimal div = denominator.abs2();
		Complex num = this.multiply(conj);
		return Complex.of(num.real().divide(div, CONTEXT), num.imaginary().divide(div, CONTEXT));
	}

	@Override
	public Complex negate() {
		return Complex.of(real().negate(CONTEXT), imaginary().negate(CONTEXT));
	}
	
	@Override
	public Complex conjugate() {
		if(isReal()) {
			return this;
		}
		return Complex.of(real(), imaginary().negate(CONTEXT));
	}
	
	@Override
	public BigDecimal abs2() {
		return real().multiply(real(), CONTEXT).add(imaginary().multiply(imaginary(), CONTEXT), CONTEXT);
	}
	
	@Override
	public BigDecimal absAsBigDecimal() {
		return abs2().sqrt(CONTEXT);
	}
	
	@Override
	public boolean isZero() {
		return isReal() && isImaginary();
	}

	@Override
	public String toString() {
		if(isZero())
			return "0";		
		if(isReal()) {
			return BigUtils.toPrettyString(real());
		}
		if(isImaginary()) {
			return String.format("%si", BigUtils.toPrettyString(imaginary()));
		}
		String real = BigUtils.toPrettyString(real());
		String im = String.format("%si", BigUtils.toPrettyString(imaginary()));
		if(BigUtils.isPositive(imaginary()))
			im = String.format("+%s", im);
		return real + im;
	}

	@Override
	public BigDecimal toBigDecimalExact() {
		if(!isReal())
			throw new ArithmeticException("This number is not real.");
		return real();
	}
	
	@Override
	public int intValueExact() {
		return toBigDecimalExact().intValueExact();
	}

	@Override
	public BigDecimal real() {
		return real;
	}

	@Override
	public BigDecimal imaginary() {
		return imaginary;
	}

	@Override
	public int hashCode() {
		if(isReal())
			return Objects.hashCode(real());
		else
			return Objects.hash(real(), imaginary());
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof Fraction f)
			return isReal() && f.isExactlyRepresentable() && Objects.equals(f.toBigDecimalExact(), real());
		return 	obj instanceof Complex c && BigUtils.equals(real(), c.real()) &&
				BigUtils.equals(imaginary(), c.imaginary());
	}
	
	
}
