package math.expressions;

record MultiplicationExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) implements MultiplicationExpression {

	@Override
	public String toString() {
		return String.format("%s*%s", first(), second());
	}
	
}
