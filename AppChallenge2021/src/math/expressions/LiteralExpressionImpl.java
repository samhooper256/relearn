package math.expressions;

import math.Complex;
import utils.MathML;

/**
 * 
 * @author Sam Hooper
 *
 */
final class LiteralExpressionImpl implements LiteralExpression {
	
	private final Complex value;
	
	public LiteralExpressionImpl(Complex value) {
		if(!value.isReal())
			throw new IllegalArgumentException(String.format("Non-real value: %s", value));
		this.value = value;
	}

	@Override
	public String toMathML() {
		return String.format(MathML.complex(value));
	}
	
	@Override
	public String toString() {
		return value().toString();
	}
	
	@Override
	public Complex value() {
		return value;
	}
	
}
