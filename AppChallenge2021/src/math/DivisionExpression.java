/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public record DivisionExpression(ConstantExpression left, ConstantExpression right) implements BinaryExpression {

	@Override
	public Complex value() {
		return left.value().divide(right.value());
	}
	
	@Override
	public String toString() {
		return String.format("[%s / %s]", left(), right());
	}
	
}