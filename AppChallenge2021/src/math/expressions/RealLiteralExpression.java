package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
record RealLiteralExpression(Complex value) implements LiteralExpression {
	
	public RealLiteralExpression {
		if(!value.isReal())
			throw new IllegalArgumentException(String.format("Non-real value: %s", value));
	}

	@Override
	public String toMathML() {
		return String.format("<mn>%s</mn>", value);
	}
	
	@Override
	public String toString() {
		return value().toString();
	}
	
}
