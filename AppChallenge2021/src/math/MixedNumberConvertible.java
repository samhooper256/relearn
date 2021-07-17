package math;

/**
 * A type that can be represented as a real-valued {@link MixedNumber} without loss of information.
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface MixedNumberConvertible {

	MixedNumber toMixedNumber();
	
}
