package base;

/**
 * Represents anything that has some conditions that must be valid. The {@link #verify()} method indicates
 * whether those conditions are met, given the relevant surrounding context as an array of objects.
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface Verifiable {
	
public final class VerificationResult {
		
		private static final VerificationResult SUCCESS = new VerificationResult("");
		
		public static final VerificationResult success() {
			return SUCCESS;
		}

		public static VerificationResult failure(String message) {
			return new VerificationResult(message);
		}
		
		public static VerificationResult failure() {
			return new VerificationResult("");
		}
		
		public static VerificationResult of(boolean result) {
			return result ? success() : failure();
		}
		
		private final String errorMessage;
		
		private VerificationResult(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		
		public boolean isSuccess() {
			return this == SUCCESS;
		}
		
		public boolean isFailure() {
			return !isSuccess();
		}
		
		public String errorMessage() {
			if(isSuccess())
				throw new IllegalStateException("No error message; the verification was successful.");
			return errorMessage;
		}
		
	}

	VerificationResult verify(Object... context);
	
}
