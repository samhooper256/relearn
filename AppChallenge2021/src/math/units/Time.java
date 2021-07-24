package math.units;

import math.Fraction;

public enum Time implements Unit<Time> {
	MILLISECOND("1/1000"), SECOND("1"), MINUTE("60"), HOUR("3600"), DAY("86400");
	
	private final Fraction factor;
	
	Time(String fracString) {
		this.factor = Fraction.of(fracString);
	}
	
	@Override
	public Fraction factor() {
		return factor;
	}
	
}
