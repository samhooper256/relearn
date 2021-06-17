/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public record ExponentiationExpression(ConstantExpression left, ConstantExpression right) implements BinaryExpression {

	@Override
	public Complex value() {
		return Complex.of(left().value().toBigDecimalExact().pow(right().value().intValueExact()));
	}
	
	@Override
	public String toString() {
		return String.format("[%s ^ %s]", left(), right());
	}
	
}
