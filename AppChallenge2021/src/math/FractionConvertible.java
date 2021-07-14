package math;

/**
 * A type that can be represented as a real-valued {@link Fraction} without loss of information.
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface FractionConvertible {

	Fraction toFraction();
	
}
