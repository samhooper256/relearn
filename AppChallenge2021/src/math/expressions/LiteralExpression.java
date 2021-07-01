package math.expressions;

import java.math.BigDecimal;

import math.*;

/**
 * <p>A {@code LiteralExpression} is an {@link Expression} representing either:
 * <ol>
 * <li>a non-negative real number</li>
 * <li>an entirely imaginary number of the form 0+bi where b is a non-negative real number</li>
 * </ol></p>
 * 
 * <p>For negative numbers or complex numbers with both a real and imaginary part, use {@link Expression#of(Complex)}.
 * </p>
 * @author Sam Hooper
 *
 */
public interface LiteralExpression extends Expression {
	
	LiteralExpression
			ONE = of(Complex.ONE),
			HALF = of(Complex.HALF);
	
	static LiteralExpression of(BigDecimal value) {
		if(BigUtils.isNegative(value))
			throw new IllegalArgumentException(String.format("Negative value: %s", value));
		return new RealLiteralExpression(Complex.of(value));
	}
	
	/** The given {@link Complex} number must be entirely {@link Complex#isReal() real} or entirely
	 * {@link Complex#isImaginary() imaginary}.*/
	static LiteralExpression of(Complex value) {
		if(value.isReal())
			return new RealLiteralExpression(value);
		else if(value.isImaginary())
			return new ImaginaryLiteralExpression(value);
		throw new IllegalArgumentException(String.format("Not entirely real nor entirely imaginary: %s", value));
	}
	
}