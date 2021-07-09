package math.expressions;

import java.util.*;

record ListExpressionImpl(List<ComplexValuedExpression> value) implements ListExpression {
	
	ListExpressionImpl {
		value = Collections.unmodifiableList(new ArrayList<>(value));
	}
	
	@Override
	public String toString() {
		return value().toString();
	}
	
}
