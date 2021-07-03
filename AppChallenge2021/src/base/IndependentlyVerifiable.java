/**
 * 
 */
package base;

/**
 * A {@link Verifiable} object that can verify itself without needing any surrounding contextual information.
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface IndependentlyVerifiable extends Verifiable {

	VerificationResult verify();
	
	@Override
	default VerificationResult verify(Object... context) {
		return verify();
	}
	
}
