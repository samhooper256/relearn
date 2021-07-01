package math.expressions;

record ExponentiationExpressionImpl(Expression base, Expression exponent) implements ExponentiationExpression {

	@Override
	public String toString() {
		return String.format("%s^%s", base(), exponent());
	}
	
}
