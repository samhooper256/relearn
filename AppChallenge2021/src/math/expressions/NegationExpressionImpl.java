package math.expressions;

record NegationExpressionImpl(Expression operand) implements NegationExpression {

	@Override
	public String toString() {
		return String.format("-%s", operand());
	}
	
}
