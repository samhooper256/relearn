package math.expressions;

import java.util.*;

public interface ListExpression extends Expression<List<ComplexValuedExpression>> {
	
	/** Equivalent to {@code value().get(index)}.*/
	default ComplexValuedExpression arg(int index) {
		return value().get(index);
	}

	/**
	 * {@inheritDoc}
	 * <p>The returned {@link List} is unmodifiable.</p>
	 */
	@Override
	List<ComplexValuedExpression> value();
	
	@Override
	default String toMathML() {
		StringJoiner j = new StringJoiner("<mo>,</mo>");
		for(ComplexValuedExpression v : value())
			j.add(v.toMathML());
		return j.toString();
	}
		
}
