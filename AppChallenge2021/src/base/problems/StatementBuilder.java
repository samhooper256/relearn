package base.problems;

import static utils.HTML.*;

import math.expressions.ComplexValuedExpression;

/**
 * <p>A {@code StatementBuilder} can be obtained via {@link Statement#builder()}.</p>
 * 
 * @author Sam Hooper
 *
 */
public final class StatementBuilder {

	StringBuilder html;
	
	StatementBuilder() {
		html = new StringBuilder();
	}
	
	public StatementBuilder addText(String text) {
		html.append(text);
		return this;
	}
	
	public StatementBuilder addExpression(ComplexValuedExpression expression) {
		html.append(ensureMathTags(expression.toMathML()));
		return this;
	}
	
	public Statement build() {
		return new StatementImpl(ensureHTMLTags(ensureBodyTags(ensureDivTags(html.toString()))));
	}
	
}
