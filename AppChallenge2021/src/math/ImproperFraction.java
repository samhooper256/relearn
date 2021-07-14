package math;

import java.math.BigInteger;

public interface ImproperFraction extends Fraction {

	static ImproperFraction of(BigInteger numerator, BigInteger denominator) {
		Fraction f = Fraction.of(numerator, denominator);
		if(!f.isImproper())
			throw new IllegalStateException("not improper");
		return (ImproperFraction) f;
	}
	
	@Override
	default boolean isProper() {
		return false;
	}
	
}
