package math.expressions;

record ParenthesizedExpressionImpl(Expression operand) implements ParenthesizedExpression {

	@Override
	public String toString() {
		return String.format("(%s)", operand);
	}
	
}
