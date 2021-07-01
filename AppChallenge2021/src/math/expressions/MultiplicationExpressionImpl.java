package math.expressions;

record MultiplicationExpressionImpl(Expression first, Expression second) implements MultiplicationExpression {

	@Override
	public String toString() {
		return String.format("%s*%s", first(), second());
	}
	
}
