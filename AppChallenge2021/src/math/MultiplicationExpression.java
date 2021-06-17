/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public record MultiplicationExpression(ConstantExpression left, ConstantExpression right) implements BinaryExpression {

	@Override
	public Complex value() {
		return left.value().multiply(right.value());
	}
	
	@Override
	public String toString() {
		return String.format("[%s * %s]", left(), right());
	}
	
}
