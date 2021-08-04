package math.units;

import math.Fraction;

public enum CustomaryLength implements Unit<CustomaryLength> {
	INCH("1/12"), FOOT("1"), YARD("3"), MILE("5280");
	
	private final Fraction factor;
	
	CustomaryLength(String fracString) {
		this.factor = Fraction.of(fracString);
	}
	
	@Override
	public Fraction factor() {
		return factor;
	}
	
}
