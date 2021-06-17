/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public record SubtractionExpression(ConstantExpression left, ConstantExpression right) implements BinaryExpression {

	@Override
	public Complex value() {
		return left.value().subtract(right.value());
	}
	
	@Override
	public String toString() {
		return String.format("[%s - %s]", left(), right());
	}
	
}
