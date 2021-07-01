package math.expressions;

import static math.BigUtils.*;

import java.math.BigDecimal;

import math.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface Expression {

	static Expression of(BigDecimal value) {
		if(isNonNegative(value))
			return LiteralExpression.of(value);
		else
			return NegationExpression.of(LiteralExpression.of(value.abs()));
	}
	
	static Expression of(Complex value) {
		if(	value.isReal() && isNonNegative(value.real()) ||
			value.isImaginary() && isNonNegative(value.imaginary()))
			return LiteralExpression.of(value);
		else if(value.isReal() && isNegative(value.real()))
			return NegationExpression.of(LiteralExpression.of(Complex.of(value.real().abs())));
		else if(value.isImaginary() && isNegative(value.imaginary()))
			return NegationExpression.of(LiteralExpression.of(Complex.of(BigDecimal.ZERO, value.imaginary().abs())));
		else if(isNonNegative(value.imaginary()))
			return 	ParenthesizedExpression.of(AdditionExpression.of(Expression.of(value.real()),
					Expression.of(value.noReal())));
		else //imaginary part is negative
			return 	ParenthesizedExpression.of(SubtractionExpression.of(Expression.of(value.real()),
					LiteralExpression.of(Complex.of(BigDecimal.ZERO, value.imaginary().abs()))));
 	}

	Complex value();
	
	String toMathML();
	
}
