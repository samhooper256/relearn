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
		return new StatementImpl(ensureHTMLTags(ensureDivTags(fixUpMathML(ensureMathTags(expression.toMathML())))));
	}
	
	/** {@code ml} should already have {@code <math>} tags.*/
	private static String fixUpMathML(String ml) {
		return String.format("<span>\u00CAg</span>%s<span>\u00CAg</span>", ml);
		/* See: https://stackoverflow.com/a/49184417/11788023
		 * When you use text in CSS, the height of a line of text is equal the largest possible height that line could
		 * be, if we used any characters. For example, the text "hello" would show with a bit of extra padding
		 * underneath to account for the fact that some letters, such as "g", have an underhang. Even though the text
		 * doesn't contain any underhanging characters that use that space, the extra padding below is still added.
		 * Likewise, characters such as Ê (\u00CA) have a diacritic on top that makes them take up more space.
		 * Therefore, extra padding is also added on the top.
		 * In MathML, however, that extra top and bottom padding is NOT added. This means there is extra space below
		 * problem statements that use normal text vs. ones that use MathML. By putting the MathML inside a div, and
		 * then adding the transparent text "Êg" to both sides (so it stays centered), we create an equal amount of
		 * padding in both expression problems and word problems. Phew!
		 * */
	}
	
	static Statement ofText(String text) {
		return new StatementImpl(ensureHTMLTags(ensureBodyTags(ensureDivTags(text))));
	}
	
	String html();
	
}
