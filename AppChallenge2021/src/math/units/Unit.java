package math.units;

import math.*;

public interface Unit<E extends Enum<E> & Unit<E>> {
	
	static <U extends Enum<U> & Unit<U>> UnitConverter<U> converter(U from, U to) {
		return UnitConverter.between(from, to);
	}
	
	Fraction factor();
	
	/** Returns a 0-based index indicating where this {@link Unit} would fall in a sorted (by ascending size) list of
	 * all units of this type. */
	int sizeIndex();
	
	/** Returns the name of this {@link Unit} in singular form in lowercase. For example,
	 * {@link CustomaryVolume#FLUIDOUNCE#singularName()} returns the String "{@code fluid ounce}" (with a space). */
	String singularName();
	
	/** Example of use:
	 * <pre><code>Time.MINUTES.convert(30, Time.HOUR)</code></pre>
	 * would convert 30 minutes to one hour and return {@code 0.5}.
	 * @param timeInThisUnit a time in the unit denoted by {@code this} object.
	 * */ 
	@SuppressWarnings("unchecked")
	default Fraction convert(Fraction timeInThisUnit, E destUnit) {
		return converter((E) this, destUnit).to(timeInThisUnit);
	}
	
	static <U extends Enum<U> & Unit<U>> String unitName(Fraction value, U unit) {
		if(value.equals(Complex.one()))
			return unit.singularName();
		else
			return pluralize(unit);
	}
	
	static <U extends Enum<U> & Unit<U>> String pluralize(U unit) {
		String name = unit.singularName();
		if(name.equals("foot"))
			return "feet";
		if(name.equals("inch"))
			return "inches";
		if(name.endsWith("s"))
			return name + "es";
		return name + "s"; 
	}
	
}
