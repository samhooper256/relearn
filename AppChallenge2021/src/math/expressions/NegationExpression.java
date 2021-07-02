package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface NegationExpression extends UnaryExpression {
	
	@Override
	default Complex value() {
		return operand().value().negate();
	}

	@Override
	default String toMathML() {
		return String.format("<mo>-</mo>%s", operand().toMathML());
	}
	
	
}
