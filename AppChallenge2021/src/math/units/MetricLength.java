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
	
}
