package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface AdditionExpression extends BinaryExpression {

	static AdditionExpression of(Expression first, Expression second) {
		return new AdditionExpressionImpl(first, second);
	}
	
	@Override
	default Complex value() {
		return first().value().add(second().value());
	}

	@Override
	default String toMathML() {
		return String.format("%s<mo>+</mo>%s", first().toMathML(), second().toMathML());
	}
	
}
