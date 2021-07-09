package math.expressions;

public record AbsoluteValueExpressionImpl(ComplexValuedExpression operand) implements AbsoluteValueExpression {
	
	@Override
	public String toString() {
		return String.format("|%s|", operand());
	}
	
}
