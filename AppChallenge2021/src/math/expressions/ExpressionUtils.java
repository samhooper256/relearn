package math.expressions;

import java.math.BigDecimal;
import java.util.*;

import math.*;

/** <p>A package-private class that provides utilities for making various {@link ComplexValuedExpression Expressions}. It is not
 * accessible outside this package because it contains methods that can (but should not) be used to create expressions
 * that would display in a way this is not mathematically equivalent to the expression represented by the tree
 * of {@code Expression} objects.</p>*/
final class ExpressionUtils {
	
	/** The given {@link BigDecimal} must be non-negative.*/
	static LiteralExpression literal(BigDecimal value) {
		if(BigUtils.isNegative(value))
			throw new IllegalArgumentException(String.format("Negative value: %s", value));
		return new RealLiteralExpression(Complex.of(value));
	}
	
	/** The given {@link Complex} number must be either:
	 * 	<ol>
	 * 	<li>A non-negative real number</li>
	 * 	<li>An {@link Complex#isImaginary() imaginary} number (that is, a complex number with no real part)
	 * 	whose {@link Complex#imaginary() imaginary part} is non-negative.</li>
	 * 	</ol>
	 */
	static LiteralExpression literal(Complex value) {
		if(value.isReal())
			return new RealLiteralExpression(value);
		else if(value.isImaginary())
			return new ImaginaryLiteralExpression(value);
		throw new IllegalArgumentException(String.format("Not entirely real nor entirely imaginary: %s", value));
	}
	
	static AdditionExpression add(ComplexValuedExpression first, ComplexValuedExpression second) {
		return new AdditionExpressionImpl(first, second);
	}
	
	static SubtractionExpression subtract(ComplexValuedExpression first, ComplexValuedExpression second) {
		return new SubtractionExpressionImpl(first, second);
	}
	
	static MultiplicationExpression multiply(ComplexValuedExpression first, ComplexValuedExpression second) {
		return new MultiplicationExpressionImpl(first, second);
	}
	
	static DivisionExpression divide(ComplexValuedExpression first, ComplexValuedExpression second) {
		return new DivisionExpressionImpl(first, second);
	}
	
	static FractionExpression fraction(ComplexValuedExpression numerator, ComplexValuedExpression denominator) {
		return new FractionExpressionImpl(numerator, denominator);
	}
	
	static ExponentiationExpression pow(ComplexValuedExpression base, ComplexValuedExpression exponent) {
		return new ExponentiationExpressionImpl(base, exponent);
	}
	
	static NegationExpression negate(ComplexValuedExpression operand) {
		return new NegationExpressionImpl(operand);
	}
	
	static SquareRootExpression sqrt(ComplexValuedExpression operand) {
		return new SquareRootExpressionImpl(operand);
	}
	
	static ParenthesizedExpression parenthesized(ComplexValuedExpression operand) {
		return new ParenthesizedExpressionImpl(operand);
	}
	
	static AbsoluteValueExpression abs(ComplexValuedExpression operand) {
		return new AbsoluteValueExpressionImpl(operand);
	}
	
	static ListExpression list(ComplexValuedExpression... arguments) {
		return list(Arrays.asList(arguments));
	}
	
	static ListExpression list(List<ComplexValuedExpression> arguments) {
		return new ListExpressionImpl(arguments);
	}
	
}
