package math;

import java.math.*;

final class MixedNumberImpl implements MixedNumber {
	
	static MixedNumberImpl from(ImproperFraction fraction) {
		BigInteger[] divmod = fraction.numerator().divideAndRemainder(fraction.denominator());
		return of(divmod[0], ProperFraction.of(divmod[1], fraction.denominator()), fraction.isNegative());
	}
	
	/** {@code integer} must be positive and {@code fraction} must be non-negative*/
	static MixedNumberImpl of(BigInteger integer, ProperFraction fraction, boolean isNegative) {
		assert BigUtils.isPositive(integer) && !fraction.isNegative();
		return new MixedNumberImpl(integer, fraction, isNegative);
	}
	
	private final BigInteger integer;
	private final ProperFraction fraction;
	private final boolean isNegative;
	
	private MixedNumberImpl(BigInteger integer, ProperFraction fraction, boolean isNegative) {
		assert BigUtils.isPositive(integer) && !fraction.isNegative();
		this.integer = integer;
		this.fraction = fraction;
		this.isNegative = isNegative;
	}

	@Override
	public MixedNumber negate() {
		return of(integer(), fraction(), !isNegative());
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
	public boolean isNegative() {
		return isNegative;
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
