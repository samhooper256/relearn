package math.expressions;

import math.*;

/**
 * <p>A {@code LiteralExpression} is an {@link Expression} representing either:
 * <ol>
 * <li>a non-negative real number</li>
 * <li>An {@link Complex#isImaginary() imaginary} number (that is, a complex number with no real part)
	 * 	whose {@link Complex#imaginary() imaginary part} is non-negative.</li>
 * </ol></p>
 * 
 * @author Sam Hooper
 *
 */
public interface LiteralExpression extends Expression {
	
}