package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface DivisionExpression extends BinaryExpression {

	static DivisionExpression of(Expression left, Expression right) {
		return new DivisionExpressionImpl(left, right);
	}
	
	@Override
	default Complex value() {
		return first().value().divide(second().value());
	}

	@Override
	default String toMathML() {
		return String.format("%s<mo>&#xF7;</mo>%s", first().toMathML(), second().toMathML());
	}
	
}
