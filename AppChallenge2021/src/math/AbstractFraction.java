package math;

import java.math.*;
import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
abstract class AbstractFraction implements Fraction {
	
	private final BigInteger numerator, denominator;
	private final boolean isNegative;
	
	protected AbstractFraction(BigInteger numerator, BigInteger denominator, boolean isNegative) {
		if(BigUtils.isNegative(numerator) || BigUtils.isNonPositive(denominator))
			throw new IllegalArgumentException(String.format("Invalid fraction: %d/%d", numerator, denominator));
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
	public BigDecimal toBigDecimal(MathContext mc) {
		BigDecimal result = new BigDecimal(numerator()).divide(new BigDecimal(denominator()), mc);
		if(isNegative())
			result = result.negate();
		return result;
	}
	
	@Override
	public BigDecimal absAsBigDecimal() {
		return real().abs();
	}
	
	@Override
	public Fraction add(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		BigInteger left = signedNumerator().multiply(f.denominator());
		BigInteger right = f.signedNumerator().multiply(denominator());
		BigInteger num = left.add(right);
		BigInteger denom = denominator().multiply(f.denominator());
		return Fraction.of(num, denom);
	}
	
	@Override
	public Fraction subtract(Complex c) {
		return add(c.negate());
	}
	
	@Override
	public Fraction negate() {
		if(isZero())
			return this;
		return Fraction.of(numerator(), denominator(), !isNegative());
	}
	
	@Override
	public Fraction multiply(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		return Fraction.of(signedNumerator().multiply(f.signedNumerator()), denominator().multiply(f.denominator()));
	}

	@Override
	public Fraction divide(Complex c) {
		if(!(c instanceof Fraction f))
			throw new IllegalArgumentException("Argument must be a Fraction");
		return multiply(f.reciprocal());
	}
	
	@Override
	public Fraction reciprocal() {
		if(isZero())
			throw new ArithmeticException("Cannot take the reciprocal of 0");
		return Fraction.of(denominator(), numerator(), isNegative());
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