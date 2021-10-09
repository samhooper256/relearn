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

	@Override
	public int sizeIndex() {
		switch(this) {
			case INCH: return 0;
			case FOOT: return 1;
			case YARD: return 2;
			case MILE: return 3;
		};
		throw new UnsupportedOperationException(String.format("Need size index for: %s", this));
	}
	
	@Override
	public String singularName() {
		return name().toLowerCase();
	}
	
}
