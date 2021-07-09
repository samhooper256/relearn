package math.expressions;

record NegationExpressionImpl(ComplexValuedExpression operand) implements NegationExpression {

	@Override
	public String toString() {
		return String.format("-%s", operand());
	}
	
}
