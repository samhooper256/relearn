package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface MultiplicationExpression extends BinaryExpression {

	static MultiplicationExpression of(Expression left, Expression right) {
		return new MultiplicationExpressionImpl(left, right);
	}
	
	@Override
	default Complex value() {
		return first().value().multiply(second().value());
	}

	@Override
	default String toMathML() {
		return String.format("%s<mo>&#xD7;</mo>%s", first().toMathML(), second().toMathML());
	}
	
}
