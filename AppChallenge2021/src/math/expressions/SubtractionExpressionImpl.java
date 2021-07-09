package math.expressions;

record SubtractionExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) implements SubtractionExpression {

	@Override
	public String toString() {
		return String.format("%s-%s", first(), second());
	}
	
}
