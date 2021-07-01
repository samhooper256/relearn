package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface BinaryExpression extends FixedArityExpression {

	@Override
	default Expression getOperand(int index) {
		return switch(index) {
			case 0 -> first();
			case 1 -> second();
			default -> throw new IllegalArgumentException(String.format("Invalid index: %d", index));
		};
	}

	Expression first();
	
	Expression second();
	
}
