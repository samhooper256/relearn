package math.units;

import math.Fraction;

public enum MetricLength implements Unit<MetricLength> {
	MILLIMETER("1/1000"), CENTIMETER("1/100"), METER("1"), KILOMETER("1000");
	
	private final Fraction factor;
	
	MetricLength(String fracString) {
		this.factor = Fraction.of(fracString);
	}
	
	@Override
	public Fraction factor() {
		return factor;
	}
	
	@Override
	public int sizeIndex() {
		switch(this) {
			case MILLIMETER: return 0;
			case CENTIMETER: return 1;
			case METER: return 2;
			case KILOMETER: return 3;
		};
		throw new UnsupportedOperationException(String.format("Need size index for: %s", this));
	}
	
	@Override
	public String singularName() {
		return name().toLowerCase();
	}
	
}
