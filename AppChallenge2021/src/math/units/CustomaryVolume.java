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
	
	@Override
	public int sizeIndex() {
		switch(this) {
			case FLUIDOUNCE: return 0;
			case CUP: return 1;
			case PINT: return 2;
			case QUART: return 3;
			case GALLON: return 4;
		};
		throw new UnsupportedOperationException(String.format("Need size index for: %s", this));
	}
	
	@Override
	public String singularName() {
		if(this == FLUIDOUNCE)
			return "fluid ounce";
		return name().toLowerCase();
	}
	
}
