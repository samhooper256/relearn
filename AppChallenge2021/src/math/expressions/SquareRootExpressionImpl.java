package math.expressions;

public record SquareRootExpressionImpl(ComplexValuedExpression operand) implements SquareRootExpression {

	@Override
	public String toString() {
		return String.format("sqrt(%s)", operand());
	}
	
}
