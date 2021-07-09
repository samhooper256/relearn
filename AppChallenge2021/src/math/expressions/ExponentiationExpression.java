package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface ExponentiationExpression extends BinaryExpression {
	
	ComplexValuedExpression base();
	
	ComplexValuedExpression exponent();

	@Override
	default ComplexValuedExpression first() {
		return base();
	}

	@Override
	default ComplexValuedExpression second() {
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
