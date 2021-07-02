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
	
	LiteralExpression
		ONE = ExpressionUtils.literal(Complex.ONE),
		HALF = ExpressionUtils.literal(Complex.HALF);
	
	static Expression of(BigDecimal value) {
		if(isNonNegative(value))
			return ExpressionUtils.literal(value);
		else
			return ExpressionUtils.literal(value.abs()).negate();
	}
	
	static Expression of(Complex value) {
		if(	value.isReal() && isNonNegative(value.real()) ||
			value.isImaginary() && isNonNegative(value.imaginary()))
			return ExpressionUtils.literal(value);
		else if(value.isReal() && isNegative(value.real()) || value.isImaginary() && isNegative(value.imaginary()))
			return of(value.negate()).negate();
		else if(isNonNegative(value.imaginary()))
			return of(value.real()).add(of(value.noReal())).parenthesized();
		else //imaginary part is negative
			return of(value.real()).subtract(of(value.noReal().negate())).parenthesized();
 	}
	
	static Expression of(String expression) {
		return Evaluator.getTree(expression);
	}

	Complex value();
	
	String toMathML();
	
	default AdditionExpression add(Expression second) {
		return ExpressionUtils.add(this, second);
	}
	
	default SubtractionExpression subtract(Expression second) {
		return ExpressionUtils.subtract(this, second);
	}
	
	default MultiplicationExpression multiply(Expression second) {
		return ExpressionUtils.multiply(this, second);
	}
	
	default DivisionExpression divide(Expression second) {
		return ExpressionUtils.divide(this, second);
	}
	
	default ExponentiationExpression pow(Expression exponent) {
		return ExpressionUtils.pow(this, exponent);
	}
	
	default NegationExpression negate() {
		return ExpressionUtils.negate(this);
	}
	
	default SquareRootExpression sqrt() {
		return ExpressionUtils.sqrt(this);
	}
	
	default ParenthesizedExpression parenthesized() {
		return ExpressionUtils.parenthesized(this);
	}
	
}