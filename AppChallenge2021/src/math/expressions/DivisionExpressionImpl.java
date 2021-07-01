package math.expressions;

record DivisionExpressionImpl(Expression first, Expression second) implements DivisionExpression {

	@Override
	public String toString() {
		return String.format("%s/%s", first(), second());
	}
	
}
