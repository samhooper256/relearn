/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public record LiteralExpression(Complex value) implements ConstantExpression {
	
	public LiteralExpression(String text) {
		this(Complex.of(text));
	}
	
	@Override
	public String toString() {
		return String.format("%s", value);
	}
	
}
