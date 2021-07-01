package math.expressions;

record AdditionExpressionImpl(Expression first, Expression second) implements AdditionExpression {

	@Override
	public String toString() {
		return String.format("%s+%s", first(), second());
	}
	
}
