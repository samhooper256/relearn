package math.expressions;

/**
 * <p>An operation with a finite, fixed, positive number of operands. Each operand expression can be retrieved
 * by the {@link #getOperand(int)} method, which accepts the 0-based index of the operand.</p>
 * @author Sam Hooper
 *
 */
public interface FixedArityExpression extends Expression {
	
	/** Returns the operand at the given 0-based index.*/
	Expression getOperand(int index);
	
}
