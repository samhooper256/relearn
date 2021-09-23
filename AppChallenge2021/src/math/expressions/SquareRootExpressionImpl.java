package math.expressions;

public final class SquareRootExpressionImpl implements SquareRootExpression {

	private final ComplexValuedExpression operand;
	
	public SquareRootExpressionImpl(ComplexValuedExpression operand) {
		this.operand = operand;
	}

	@Override
	public String toString() {
		return String.format("sqrt(%s)", operand());
	}

	@Override
	public ComplexValuedExpression operand() {
		return operand;
	}
	
}
