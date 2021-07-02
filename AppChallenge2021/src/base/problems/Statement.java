package base.problems;

import math.expressions.Expression;
import utils.HTML;

/**
 * <p>A {@link Problem} statement.</p>
 * 
 * @author Sam Hooper
 *
 */
public interface Statement {

	static StatementBuilder builder() {
		return new StatementBuilder();
	}
	
	static Statement fromExpression(Expression expression) {
		return new StatementImpl(HTML.ensureHTMLTags(HTML.ensureMathTags(expression.toMathML())));
	}
	
	static Statement ofText(String text) {
		return new StatementImpl(HTML.ensureHTMLTags(text));
	}
	
	String html();
	
}
