package math;

import java.math.*;

final class MixedNumberImpl implements MixedNumber {
	
	static MixedNumberImpl from(ImproperFraction fraction) {
		BigInteger[] res = fraction.numerator().divideAndRemainder(fraction.denominator());
		BigInteger integer = res[0];
		if(fraction.isNegative())
			integer = integer.negate();
		return of(integer, ProperFraction.of(res[1], fraction.denominator()));
	}
	
	/** {@code integer} must not be zero and {@code fraction} must not be negative.*/
	static MixedNumberImpl of(BigInteger integer, ProperFraction fraction) {
		if(BigUtils.isZero(integer))
			throw new IllegalArgumentException("integer part must not be zero");
		if(fraction.isNegative())
			throw new IllegalArgumentException("fractional part must not be negative");
		return new MixedNumberImpl(integer, fraction);
	}
	
	private final BigInteger integer;
	private final ProperFraction fraction;
	
	private MixedNumberImpl(BigInteger integer, ProperFraction fraction) {
		this.integer = integer;
		this.fraction = fraction;
	}

	@Override
	public Complex negate() {
		BigInteger negatedInteger = integer().negate();
		return of(negatedInteger, fraction);
	}

	@Override
	public BigInteger integer() {
		return integer;
	}

	@Override
	public ProperFraction fraction() {
		return fraction;
	}
	
	@Override
	public int hashCode() {
		return real().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof FractionConvertible fc)
			return toFraction().equals(fc.toFraction());
		if(obj instanceof Complex c)
			return c.isReal() && isExactlyRepresentable() && toBigDecimalExact().equals(c.real());
		return false;
	}

	@Override
	public String toString() {
		return String.format("%d %s", integer(), fraction());
	}
}
