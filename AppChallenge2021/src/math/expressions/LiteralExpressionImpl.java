package math.expressions;

import math.Complex;
import utils.MathML;

/**
 * 
 * @author Sam Hooper
 *
 */
record LiteralExpressionImpl(Complex value) implements LiteralExpression {
	
	public LiteralExpressionImpl {
		if(!value.isReal())
			throw new IllegalArgumentException(String.format("Non-real value: %s", value));
	}

	@Override
	public String toMathML() {
		return String.format(MathML.complex(value));
	}
	
	@Override
	public String toString() {
		return value().toString();
	}
	
}
