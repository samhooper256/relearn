package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface AdditionExpression extends BinaryExpression {

	@Override
	default Complex value() {
		return first().value().add(second().value());
	}

	@Override
	default String toMathML() {
		return String.format("%s<mo>+</mo>%s", first().toMathML(), second().toMathML());
	}
	
}
