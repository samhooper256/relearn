package math.expressions;

final class ExponentiationExpressionImpl implements ExponentiationExpression {

	private final ComplexValuedExpression base;
	private final ComplexValuedExpression exponent;

	public ExponentiationExpressionImpl(ComplexValuedExpression base, ComplexValuedExpression exponent) {
		this.base = base;
		this.exponent = exponent;
	}

	@Override
	public String toString() {
		return String.format("%s^%s", base(), exponent());
	}

	@Override
	public ComplexValuedExpression base() {
		return base;
	}

	@Override
	public ComplexValuedExpression exponent() {
		return exponent;
	}
	
}
