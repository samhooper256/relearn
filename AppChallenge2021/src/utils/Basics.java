package utils;

public final class Basics {

	private Basics() {
		
	}
	
	public static int clamp(int val, int min, int max) {
		if(val < min)
			val = min;
		else if(val > max)
			val = max;
		return val;
	}
	
}
