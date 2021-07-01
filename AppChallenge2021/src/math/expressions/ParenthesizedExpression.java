package math.expressions;

import math.Complex;

/**
 * @author Sam Hooper
 *
 */
public interface ParenthesizedExpression extends UnaryExpression {

	static ParenthesizedExpression of(Expression expression) {
		return new ParenthesizedExpressionImpl(expression);
	}
	
	@Override
	default Complex value() {
		return operand().value();
	}

	@Override
	default String toMathML() {
		return String.format("<mo>(</mo>%s<mo>)</mo>", operand().toMathML());
	}
	
}
