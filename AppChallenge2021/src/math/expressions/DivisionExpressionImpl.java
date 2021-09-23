package math.expressions;

final class DivisionExpressionImpl implements DivisionExpression {

	private final ComplexValuedExpression first;
	private final ComplexValuedExpression second;

	public DivisionExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) {
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return String.format("%s/%s", first(), second());
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
