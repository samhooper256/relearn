package math.expressions;

/**
 * <p>Represents a function that is to be rendered in function notation, such as {@code max(2, 3)}.</p>
 * 
 * <p>This interface should <b>not</b> be extended or implemented by types representing an operation that, while
 * conceptually a function, should be rendered in a different manner than function notation (examples include
 * {@link AdditionExpression addition}, {@link NegationExpression negation}, and {@link SquareRootExpression square
 * rooting}).</p>
 * @author Sam Hooper
 *
 */
public interface FunctionExpression extends ComplexValuedExpression {
	
	String name();
	
	/** Returns a {@link ListExpression} containing the arguments to this {@link FunctionExpression}.*/
	ListExpression arguments();

	@Override
	default String toMathML() {
		return String.format("<mo>%s</mo><mo>(</mo>%s<mo>)</mo>", name(), arguments().toMathML());
	}
	
}
