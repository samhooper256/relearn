package base.problems;

import math.expressions.Expression;
import utils.HTML;

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
	
	public StatementBuilder addExpression(Expression expression) {
		html.append(HTML.ensureMathTags(expression.toMathML()));
		return this;
	}
	
	public Statement build() {
		return new StatementImpl(HTML.ensureHTMLTags(html.toString()));
	}
	
}
