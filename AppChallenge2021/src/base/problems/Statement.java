package base.problems;

import static utils.HTML.*;

import math.expressions.Expression;

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
		return new StatementImpl(ensureHTMLTags(ensureBodyTags(ensureDivTags(ensureMathTags(expression.toMathML())))));
	}
	
	static Statement ofText(String text) {
		return new StatementImpl(ensureHTMLTags(ensureBodyTags(ensureDivTags(text))));
	}
	
	String html();
	
}
