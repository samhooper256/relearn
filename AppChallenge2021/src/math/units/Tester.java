package math.units;

import java.util.List;

import math.*;
import utils.RNG;

class Tester {
	
	public static void main(String[] args) {
//		Time sec = Time.SECOND, min = Time.MINUTE;
//		System.out.println(sec.convert(30, Time.MINUTE));
//		System.out.println(min.convert(20, Time.HOUR));
//		System.out.println(Time.SECOND.convert(0.5, Time.MILLISECOND));
//		
//		System.out.println("Hours = " + Unit.converter(Time.HOUR, Time.DAY).to(36));
//		System.out.println("Days = " + Unit.converter(Time.HOUR, Time.DAY).from(3));
		
//		List<Time> list = RNG.pick2Unique(Time.values());
//		Time smaller = list.get(0);
//		Time larger = list.get(1);
//		if(smaller.factor() > larger.factor()) {
//			Time temp = smaller;
//			smaller = larger;
//			larger = temp;
//		}
//		int valInLargerUnits = RNG.intInclusive(1, 10);
//		
//		System.out.printf("%d %s is how many %s?%n", valInLargerUnits,
//				Unit.unitName(valInLargerUnits, larger), 
//				Unit.pluralize(smaller));
//		System.out.printf("%s%n", Unit.converter(larger, smaller).to(valInLargerUnits));
		
		List<Time> list = RNG.pick2Unique(Time.values());
		Time from = list.get(0);
		Time to = list.get(1);
		Fraction valInFromUnits = Complex.one();
		
		System.out.printf("%d %s is how many %s?%n", valInFromUnits,
				Unit.unitName(valInFromUnits, from), 
				Unit.pluralize(to));
		System.out.printf("%s%n", Unit.converter(from, to).to(valInFromUnits));
	}
	
}
