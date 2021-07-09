package math.expressions;

import math.Complex;

public interface AbsoluteValueExpression extends UnaryExpression {

	@Override
	default Complex value() {
		return operand().value().abs();
	}

	@Override
	default String toMathML() {
		return String.format("<mfenced open=\"|\" close=\"|\"><mrow>%s</mrow></mfenced></math>", operand().toMathML());
	}
	
}
