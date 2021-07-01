package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface NegationExpression extends UnaryExpression {

	static NegationExpression of(Expression operand) {
		return new NegationExpressionImpl(operand);
	}
	
	@Override
	default Complex value() {
		return operand().value().negate();
	}

	@Override
	default String toMathML() {
		return String.format("<mo>-</mo>%s", operand().toMathML());
	}
	
	
}
