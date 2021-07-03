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
		
		private static final VerificationResult
				SUCCESS = new VerificationResult(""),
				FAILURE_WITHOUT_MESSAGE = new VerificationResult("");
		
		public static final VerificationResult success() {
			return SUCCESS;
		}

		public static VerificationResult failure(String message) {
			return new VerificationResult(message);
		}
		
		public static VerificationResult failure() {
			return FAILURE_WITHOUT_MESSAGE;
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
		
		public boolean hasMessage() {
			return isFailure() && !errorMessage().isEmpty();
		}
		
		public String errorMessage() {
			if(isSuccess())
				throw new IllegalStateException("No error message; the verification was successful.");
			return errorMessage;
		}
		public VerificationResult and(VerificationResult o) {
			if(isSuccess() && o.isSuccess())
				return this;
			String message;
			if(hasMessage())
				if(o.hasMessage())
					message = String.format("%s; %s", errorMessage(), o.errorMessage());
				else
					message = errorMessage();
			else
				message = o.errorMessage();
			return failure(message);
		}
		
	}

	VerificationResult verify(Object... context);
	
}
