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
	
	@Override
	public int sizeIndex() {
		switch(this) {
			case MILLISECOND: return 0;
			case SECOND: return 1;
			case MINUTE: return 2;
			case HOUR: return 3;
			case DAY: return 4;
		};
		throw new UnsupportedOperationException(String.format("Need size index for: %s", this));
	}
	
	@Override
	public String singularName() {
		return name().toLowerCase();
	}
	
}
