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

	/** The result will be a {@link MixedNumber} whenever possible.*/
	@Override
	public Complex add(Complex c) {
		final Fraction f;
		if(c instanceof Fraction ff)
			f = ff;
		else if(c instanceof MixedNumber m)
			f = m.toImproperFraction();
		else
			return Complex.of(real()).add(c);
		Complex result = toImproperFraction().add(f);
		return result instanceof ImproperFraction x ? from(x) : result;
	}

	/** The result will be a {@link MixedNumber} whenever possible.*/
	@Override
	public Complex subtract(Complex c) {
		return add(c.negate());
	}

	/** The result will be a {@link MixedNumber} whenever possible.*/
	@Override
	public Complex multiply(Complex c) {
		final Fraction f;
		if(c instanceof Fraction ff)
			f = ff;
		else if(c instanceof MixedNumber m)
			f = m.toImproperFraction();
		else
			return Complex.of(real()).multiply(c);
		Complex result = toImproperFraction().multiply(f);
		return result instanceof ImproperFraction x ? from(x) : result;
	}

	/** The result will be a {@link MixedNumber} whenever possible.*/
	@Override
	public Complex divide(Complex c) {
		final Fraction f;
		if(c instanceof Fraction ff)
			f = ff;
		else if(c instanceof MixedNumber m)
			f = m.toImproperFraction();
		else
			return Complex.of(real()).divide(c);
		Complex result = toImproperFraction().divide(f);
		return result instanceof ImproperFraction x ? from(x) : result;
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
	
	
}
