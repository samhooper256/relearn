package math.expressions;

record AdditionExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) implements AdditionExpression {

	@Override
	public String toString() {
		return String.format("%s+%s", first(), second());
	}
	
}
