package math.expressions;

import math.Complex;

/**
 * 
 * @author Sam Hooper
 *
 */
public record ImaginaryLiteralExpression(Complex value) implements LiteralExpression {
	
	public ImaginaryLiteralExpression {
		if(!value.isImaginary())
			throw new IllegalArgumentException(String.format("Non-imaginary value: %s", value));
	}

	@Override
	public String toMathML() {
		return String.format("<mi>%s</mi>", value);
	}

	@Override
	public String toString() {
		return value().toString();
	}
	
}
