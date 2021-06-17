/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public class NegatedExpression implements UnaryExpression {
	
	private final ConstantExpression operand;
	
	public NegatedExpression(ConstantExpression operand) {
		this.operand = operand;
	}

	@Override
	public Complex value() {
		return operand.value().negate();
	}

	@Override
	public ConstantExpression operand() {
		return operand;
	}
	
	@Override
	public String toString() {
		return String.format("[-%s]", operand());
	}
	
}
