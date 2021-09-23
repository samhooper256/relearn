package math.expressions;

final class NegationExpressionImpl implements NegationExpression {

	private final ComplexValuedExpression operand;
	
	public NegationExpressionImpl(ComplexValuedExpression operand) {
		this.operand = operand;
	}

	@Override
	public String toString() {
		return String.format("-%s", operand());
	}

	@Override
	public ComplexValuedExpression operand() {
		return operand;
	}
	
}
