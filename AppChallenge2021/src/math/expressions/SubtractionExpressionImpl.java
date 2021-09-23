package math.expressions;

final class SubtractionExpressionImpl implements SubtractionExpression {

	private final ComplexValuedExpression first;
	private final ComplexValuedExpression second;

	

	public SubtractionExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", first(), second());
	}

	@Override
	public ComplexValuedExpression first() {
		return first;
	}

	@Override
	public ComplexValuedExpression second() {
		return second;
	}
	
}
