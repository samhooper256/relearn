package base.problems;

import static utils.HTML.*;
import static utils.MathML.*;

import java.math.*;

import math.*;
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
	
	private StatementBuilder addOpeningMathTag() {
		html.append("<math>");
		return this;
	}
	
	private StatementBuilder addClosingMathTag() {
		html.append("</math>");
		return this;
	}
	
	public StatementBuilder addText(String text) {
		html.append(text);
		return this;
	}
	
	public StatementBuilder addExpression(ComplexValuedExpression expression) {
		html.append(ensureMathTags(expression.toMathML()));
		return this;
	}
	
	public StatementBuilder addNumber(long number) {
		return addNumber(BigInteger.valueOf(number));
	}

	public StatementBuilder addNumber(double number) {
		return addNumber(BigDecimal.valueOf(number));
	}
	
	public StatementBuilder addNumber(BigInteger bi) {
		return addOpeningMathTag().addIntegerWithoutMathTags(bi).addClosingMathTag();
	}
	
	public StatementBuilder addNumber(BigDecimal bd) {
		return addOpeningMathTag().addDecimalWithoutMathTags(bd).addClosingMathTag();
	}
	
	/**
	 * If the given {@link Fraction} is an integer, displays it as one. Otherwise, displays it as if by
	 * {@link #addFraction(Fraction)}.  
	 */
	public StatementBuilder addNumber(Fraction fraction) {
		if(fraction.isInteger())
			return addNumber(fraction.numerator());
		else
			return addFraction(fraction);
	}
	
	private StatementBuilder addIntegerWithoutMathTags(BigInteger bi) {
		html.append(integer(bi));
		return this;
	}
	
	private StatementBuilder addDecimalWithoutMathTags(BigDecimal bd) {
		html.append(decimal(bd));
		return this;
	}
	
	public StatementBuilder addMixedNumber(MixedNumber mn) {
		return 	addOpeningMathTag()
				.addIntegerWithoutMathTags(mn.integer())
				.addFractionWithoutMathTags(mn.fraction())
				.addClosingMathTag();
	}
	
	/** Displays as a numerator and denominator, even if the given {@link Fraction} is an integer.*/
	public StatementBuilder addFraction(Fraction f) {
		return addOpeningMathTag().addFractionWithoutMathTags(f).addClosingMathTag();
	}
	
	private StatementBuilder addFractionWithoutMathTags(Fraction f) {
		html.append(fraction(f));
		return this;
	}
	
	public Statement build() {
		return new StatementImpl(ensureHTMLTags(ensureBodyTags(ensureDivTags(html.toString()))));
	}
	
}
