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
	public Fraction addFraction(Fraction f) {
		BigInteger left = signedNumerator().multiply(f.denominator());
		BigInteger right = f.signedNumerator().multiply(denominator());
		BigInteger num = left.add(right);
		BigInteger denom = denominator().multiply(f.denominator());
		return Fraction.of(num, denom);
	}
	
	@Override
	public Fraction subtractFraction(Fraction f) {
		return addFraction(f.negate());
	}
	
	@Override
	public Fraction negate() {
		if(isZero())
			return this;
		return Fraction.of(numerator(), denominator(), !isNegative());
	}
	
	@Override
	public Fraction multiplyFraction(Fraction f) {
		return Fraction.of(signedNumerator().multiply(f.signedNumerator()), denominator().multiply(f.denominator()));
	}

	@Override
	public Fraction divideFraction(Fraction f) {
		return multiplyFraction(f.reciprocal());
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
		return 	String.format("%s[isNegative=%b, numerator=%d, denominator=%d]",
				getClass().getSimpleName(), isNegative, numerator, denominator);
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
	public boolean isExactlyRepresentable() {
		return isRealAndExactlyRepresentable();
	}
	
	@Override
	public int hashCode() {
		return real().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof FractionConvertible) {
			Fraction f = ((FractionConvertible) obj).toFraction();
			return Objects.equals(denominator(), f.denominator()) && isNegative() == f.isNegative() &&
					Objects.equals(numerator(), f.numerator());
		}
		if(obj instanceof Complex) {
			Complex c = (Complex) obj;
			return c.isReal() && isRealAndExactlyRepresentable() && toBigDecimalExact().equals(c.real());
		}
		return false;
	}
	
}
