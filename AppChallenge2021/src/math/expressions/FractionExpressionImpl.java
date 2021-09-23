package math.expressions;

final class FractionExpressionImpl implements FractionExpression {

	ComplexValuedExpression numerator;
	ComplexValuedExpression denominator;
	
	public FractionExpressionImpl(ComplexValuedExpression numerator, ComplexValuedExpression denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public String toString() {
		return String.format("frac(%s, %s)", numerator(), denominator());
	}

	@Override
	public ComplexValuedExpression numerator() {
		return numerator;
	}

	@Override
	public ComplexValuedExpression denominator() {
		return denominator;
	}
	
}