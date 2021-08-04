package math.units;

import math.Fraction;

public enum CustomaryVolume implements Unit<CustomaryVolume> {
	FLUIDOUNCE("1/8"), CUP("1"), PINT("2"), QUART("4"), GALLON("16");
	
	private final Fraction factor;
	
	CustomaryVolume(String fracString) {
		this.factor = Fraction.of(fracString);
	}
	
	@Override
	public Fraction factor() {
		return factor;
	}
	
}
