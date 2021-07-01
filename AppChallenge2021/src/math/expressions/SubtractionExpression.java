package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface SubtractionExpression extends BinaryExpression {

	static SubtractionExpression of(Expression first, Expression second) {
		return new SubtractionExpressionImpl(first, second);
	}
	
	@Override
	default Complex value() {
		return first().value().subtract(second().value());
	}

	@Override
	default String toMathML() {
		return String.format("%s<mo>-</mo>%s", first().toMathML(), second().toMathML());
	}
	
}
