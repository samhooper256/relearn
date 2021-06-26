/**
 * 
 */
package utils;

/**
 * @author Sam Hooper
 *
 */
public final class Assertions {
	
	private Assertions() {
		
	}
	
	public static void require() {
		require("Assertions are not enabled.");
	}
	
	public static void require(String message) {
		if(!areEnabled()) {
			System.err.println(message);
			System.exit(-1);
		}
	}
	
	public static boolean areEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}
	
}
