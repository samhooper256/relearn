package math.expressions;

public record FractionExpressionImpl(Expression numerator, Expression denominator) implements FractionExpression {

	@Override
	public String toString() {
		return String.format("frac(%s, %s)%n", numerator(), denominator());
	}
	
}