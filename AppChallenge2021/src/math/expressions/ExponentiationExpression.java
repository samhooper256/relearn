package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface ExponentiationExpression extends BinaryExpression {
	
	Expression base();
	
	Expression exponent();

	@Override
	default Expression first() {
		return base();
	}

	@Override
	default Expression second() {
		return exponent();
	}

	@Override
	default Complex value() {
		return Complex.of(base().value().toBigDecimalExact().pow(exponent().value().intValueExact()));
	}

	@Override
	default String toMathML() {
		return String.format("<msup>%s%s</msup>", base().toMathML(), exponent().toMathML());
	}
	
}
