package math.expressions;

record DivisionExpressionImpl(ComplexValuedExpression first, ComplexValuedExpression second) implements DivisionExpression {

	@Override
	public String toString() {
		return String.format("%s/%s", first(), second());
	}
	
}
