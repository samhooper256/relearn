package math.expressions;

record ExponentiationExpressionImpl(ComplexValuedExpression base, ComplexValuedExpression exponent) implements ExponentiationExpression {

	@Override
	public String toString() {
		return String.format("%s^%s", base(), exponent());
	}
	
}
