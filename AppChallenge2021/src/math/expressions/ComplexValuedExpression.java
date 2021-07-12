package math.expressions;

import static math.BigUtils.*;

import java.math.*;

import math.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface ComplexValuedExpression extends Expression<Complex> {
	
	LiteralExpression
		ONE = ExpressionUtils.literal(Complex.ONE),
		HALF = ExpressionUtils.literal(Complex.HALF);
	
	static ComplexValuedExpression of(BigInteger value) {
		return of(new BigDecimal(value));
	}
	
	static ComplexValuedExpression of(BigDecimal value) {
		if(isNonNegative(value))
			return ExpressionUtils.literal(value);
		else
			return ExpressionUtils.literal(value.abs()).negate();
	}
	
	static ComplexValuedExpression of(Complex value) {
		if(value instanceof Fraction f) {
			ComplexValuedExpression e = of(f.numerator()).over(of(f.denominator()));
			if(f.isNegative())
				e = e.negate();
			return e;
		}
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
	
	/** Returns a {@link ComplexValuedExpression} representing the given {@code expression}. If the expression contains
	 * undefined constructs, such as dividing by zero, those constructs are not detected by this method. Instead,
	 * an exception will be thrown by {@link #value()}.*/
	static ComplexValuedExpression of(String expression) {
		return Evaluator.getTree(expression);
	}
	
	default AdditionExpression add(ComplexValuedExpression second) {
		return ExpressionUtils.add(this, second);
	}
	
	default SubtractionExpression subtract(ComplexValuedExpression second) {
		return ExpressionUtils.subtract(this, second);
	}
	
	default MultiplicationExpression multiply(ComplexValuedExpression second) {
		return ExpressionUtils.multiply(this, second);
	}
	
	default DivisionExpression divide(ComplexValuedExpression second) {
		return ExpressionUtils.divide(this, second);
	}
	
	default ExponentiationExpression pow(ComplexValuedExpression exponent) {
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
	
	default FractionExpression over(ComplexValuedExpression denominator) {
		return ExpressionUtils.fraction(this, denominator);
	}
	
	default AbsoluteValueExpression abs() {
		return ExpressionUtils.abs(this);
	}
	
}