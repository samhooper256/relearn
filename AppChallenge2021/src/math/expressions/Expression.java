package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 * @param <V> the type of this {@code Expression's} {@link #value()}. 
 */
public interface Expression<V> {

	/** <p>Returns the value of this {@link Expression}. This method never returns {@code null}.
	 * For any two invocations {@code a} and {@code b} of {@code value()}, it must be the case that
	 * {@code a.equals(b)}. That is, the value of this {@code Expression} does not change.</p>*/
	V value();
	
	/** The returned {@code String} does <em>not</em> contain {@code <math>} tags.*/
	String toMathML();
	
}
