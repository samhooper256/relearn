package math;

import java.math.*;
import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
final class FractionImpl implements Fraction {
	
	public static final FractionImpl ZERO = new FractionImpl(BigInteger.ZERO, BigInteger.ONE, false);
	
	private final BigInteger numerator, denominator;
	private final boolean isNegative;
	
	static FractionImpl ofImpl(long numerator, long denominator) {
		return ofImpl(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
	}
	
	static FractionImpl ofImpl(BigInteger numerator, BigInteger denominator) {
		if(BigUtils.isZero(numerator))
			return ZERO;
		if(BigUtils.isZero(denominator))
			throw new IllegalArgumentException("denominator cannot be zero");
		boolean isNegative = BigUtils.isNegative(numerator) ^ BigUtils.isNegative(denominator);
		numerator = numerator.abs();
		denominator = denominator.abs();
		return new FractionImpl(numerator, denominator, isNegative);
	}
	
	private FractionImpl(BigInteger numerator, BigInteger denominator, boolean isNegative) {
		if(BigUtils.isNegative(numerator) || BigUtils.isNegative(denominator))
			throw new IllegalArgumentException("BigInteger arguments must not be negative");
		BigInteger gcd = MathUtils.gcd(numerator, denominator);
		this.numerator = numerator.divide(gcd);
		this.denominator = denominator.divide(gcd);
		this.isNegative = isNegative;
	}
	
	@Override
	public BigInteger numerator() {
		return numerator;
	}

	@Override
	public BigInteger denominator() {
		return denominator;
	}
	
	@Override
	public boolean isNegative() {
		return isNegative;
	}
	
	@Override
	public boolean isProper() {
		return numerator().compareTo(denominator()) < 0;
	}
	
	@Override
	public boolean isImproper() {
		return !isProper();
	}
	
	@Override
	public BigDecimal toBigDecimal(MathContext mc) {
		BigDecimal result = new BigDecimal(numerator()).divide(new BigDecimal(denominator()), mc);
		if(isNegative())
			result = result.negate();
		return result;
	}
	
	@Override
	public BigDecimal real() {
		return toBigDecimal(MathContext.DECIMAL64);
	}
	
	@Override
	public BigDecimal imaginary() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public boolean isReal() {
		return true;
	}

	@Override
	public boolean isImaginary() {
		return false;
	}

	@Override
	public BigDecimal abs() {
		return real().abs();
	}
	
	@Override
	public Fraction add(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		BigInteger left = numerator().multiply(f.denominator());
		BigInteger right = f.numerator().multiply(denominator());
		BigInteger num = left.add(right);
		BigInteger denom = denominator().multiply(f.denominator());
		return ofImpl(num, denom);
	}
	
	@Override
	public Fraction subtract(Complex c) {
		return add(c.negate());
	}
	
	@Override
	public Fraction negate() {
		if(isZero())
			return this;
		return new FractionImpl(numerator(), denominator(), !isNegative());
	}

	
	
	@Override
	public Fraction multiply(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		return ofImpl(numerator().multiply(f.numerator()), denominator().multiply(f.denominator()));
	}

	@Override
	public Fraction divide(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		return this.multiply(f.reciprocal());
	}
	
	@Override
	public Fraction reciprocal() {
		if(isZero())
			throw new ArithmeticException("Cannot take the reciprocal of 0");
		return new FractionImpl(denominator(), numerator(), isNegative());
	}

	@Override
	public Complex conjugate() {
		return this;
	}

	@Override
	public boolean isZero() {
		return BigUtils.isZero(numerator());
	}

	@Override
	public String toString() {
		return String.format("%s%d/%d", isNegative ? "-" : "", numerator(), denominator());
	}

	@Override
	public BigDecimal toBigDecimalExact() {
		return toBigDecimal(MathContext.UNLIMITED);
	}

	@Override
	public int intValueExact() {
		return toBigDecimalExact().intValueExact();
	}

	@Override
	public int hashCode() {
		return real().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(obj instanceof Fraction f)
			return Objects.equals(denominator(), f.denominator()) && isNegative() == f.isNegative() &&
					Objects.equals(numerator(), f.numerator());
		else if(obj instanceof Complex c)
			return c.isReal() && isExactlyRepresentable() && toBigDecimalExact().equals(c.real());
		return false;
	}
	
}
