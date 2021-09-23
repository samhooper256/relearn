package math.expressions;

final class ParenthesizedExpressionImpl implements ParenthesizedExpression {

	private final ComplexValuedExpression operand;
	
	public ParenthesizedExpressionImpl(ComplexValuedExpression operand) {
		this.operand = operand;
	}

	@Override
	public String toString() {
		return String.format("(%s)", operand);
	}

	@Override
	public ComplexValuedExpression operand() {
		return operand;
	}
	
}
