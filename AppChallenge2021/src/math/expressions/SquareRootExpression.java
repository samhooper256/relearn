package math.expressions;

import java.math.MathContext;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface SquareRootExpression extends UnaryExpression {

	@Override
	default Complex value() {
		return Complex.of(operand().value().toBigDecimalExact().sqrt(MathContext.DECIMAL64));
	}

	@Override
	default String toMathML() {
		return String.format("<msqrt>%s</msqrt>", operand().toMathML());
	}
	
}
