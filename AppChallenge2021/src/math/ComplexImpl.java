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
	public Complex subtract(Complex c) {
		return add(c.negate());
	}
	
	@Override
	public Complex negate() {
		return Complex.of(real().negate(CONTEXT), imaginary().negate(CONTEXT));
	}
	
	@Override
	public Complex conjugate() {
		if(isReal())
			return this;
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
		return String.format("%s[real=%s, imaginary=%s]", getClass().getSimpleName(), BigUtils.toPrettyString(real),
				BigUtils.toPrettyString(imaginary));
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
	
	/** Returns a string that is {@link Complex#isValidInRectangularForm(String) valid in rectangular form} and that
	 * represents the same number as {@code this}.*/
	@Override
	public String toParsableString() {
		if(isZero())
			return "0";
		if(isReal())
			return BigUtils.toPrettyString(real());
		if(isImaginary())
			return String.format("%si", BigUtils.toPrettyString(imaginary()), "i");
		StringBuilder result = new StringBuilder(BigUtils.toPrettyString(real()));
		if(BigUtils.isNonNegative(imaginary()))
			result.append('+');
		return result.append(BigUtils.toPrettyString(imaginary())).append('i').toString();
		
	}
	
}
