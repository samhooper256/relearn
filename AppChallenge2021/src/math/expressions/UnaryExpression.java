package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface UnaryExpression extends FixedArityExpression {

	@Override
	default Expression getOperand(int index) {
		if(index != 0)
			throw new IllegalArgumentException(String.format("Invalid operand index: %d", index));
		return operand();
	}

	Expression operand();
	
}
