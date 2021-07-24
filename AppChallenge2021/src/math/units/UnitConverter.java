package math.units;

import math.Fraction;

public final class UnitConverter<U extends Enum<U> & Unit<U>> {
	
	public static <U extends Enum<U> & Unit<U>> UnitConverter<U> between(U from, U to) {
		return new UnitConverter<>(from, to);
	}
	
	private final U from, to;
	
	private UnitConverter(U from, U to) {
		this.from = from;
		this.to = to;
	}
	
	public Fraction to(Fraction valueInFromUnits) {
		return valueInFromUnits.multiplyFraction(from.factor()).divideFraction(to.factor());
	}
	
	public Fraction from(Fraction valueInToUnits) {
		return valueInToUnits.multiplyFraction(to.factor()).divideFraction(from.factor());
	}
	
	public U fromUnit() {
		return from;
	}
	
	public U toUnit() {
		return from;
	}
	
}
