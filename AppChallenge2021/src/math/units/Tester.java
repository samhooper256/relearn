package math.units;

class Tester {
	
	public static void main(String[] args) {
		Time sec = Time.SECOND, min = Time.MINUTE;
		System.out.println(sec.convert(30, Time.MINUTE));
		System.out.println(min.convert(20, Time.HOUR));
		System.out.println(Time.SECOND.convert(0.5, Time.MILLISECOND));
	}
	
}
