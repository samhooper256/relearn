package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface FractionExpression extends BinaryExpression {
	
	Expression numerator();
	
	Expression denominator();
	
	@Override
	default Expression first() {
		return numerator();
	}
	
	@Override
	default Expression second() {
		return denominator();
	}

	@Override
	default Complex value() {
		return numerator().value().divide(denominator().value());
	}

	@Override
	default String toMathML() {
		return String.format("<mfrac>%s%s</mfrac>", numerator().toMathML(), denominator().toMathML());
	}
	
}
