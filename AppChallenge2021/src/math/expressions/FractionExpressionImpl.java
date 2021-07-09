package math.expressions;

public record FractionExpressionImpl(ComplexValuedExpression numerator, ComplexValuedExpression denominator) implements FractionExpression {

	@Override
	public String toString() {
		return String.format("frac(%s, %s)%n", numerator(), denominator());
	}
	
}