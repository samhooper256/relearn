package math.expressions;

record ParenthesizedExpressionImpl(ComplexValuedExpression operand) implements ParenthesizedExpression {

	@Override
	public String toString() {
		return String.format("(%s)", operand);
	}
	
}
