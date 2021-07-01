package math.expressions;

record SubtractionExpressionImpl(Expression first, Expression second) implements SubtractionExpression {

	@Override
	public String toString() {
		return String.format("%s-%s", first(), second());
	}
	
}
