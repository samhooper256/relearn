package math.expressions;

import java.util.*;

final class ListExpressionImpl implements ListExpression {
	
	private final List<ComplexValuedExpression> value;
	
	ListExpressionImpl(List<ComplexValuedExpression> value){
		this.value = Collections.unmodifiableList(new ArrayList<>(value));
	}
	
	@Override
	public String toString() {
		return value().toString();
	}

	@Override
	public List<ComplexValuedExpression> value() {
		return value;
	}
	
}
