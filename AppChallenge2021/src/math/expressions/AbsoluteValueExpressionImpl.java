package math.expressions;

final class AbsoluteValueExpressionImpl implements AbsoluteValueExpression {
	
	private final ComplexValuedExpression operand;
	
	AbsoluteValueExpressionImpl(ComplexValuedExpression operand) {
		this.operand = operand;
	}
	
	@Override
	public String toString() {
		return String.format("|%s|", operand());
	}
	
	@Override
	public ComplexValuedExpression operand() {
		return operand;
	}
	
}
