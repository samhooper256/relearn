package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import javafx.scene.control.*;
import javafx.util.Duration;
import math.*;

public enum MathAnswerMode {
	/* The ordering of the enum constants IS significant because it is the canonical order that the modes will be
	 * displayed to the user.*/
	
	/** Accepts all integers, possibly with a leading sign.*/
	INTEGER(makeIcon("-7", "Integer answers are allowed.")) {
		
		@Override
		public boolean isValid(String str) {
			return BigUtils.isValidBigInteger(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Complex.of(new BigDecimal(str));
		}
		
	},
	/** Accepts all {@link BigUtils#isValidBigDecimal(String) valid} {@link BigDecimal BigDecimals}. In other words,
	 * accepts all real integer and decimal answers.*/
	REAL_DECIMAL(makeIcon("1.5", "Integer or decimal answers are allowed.")) {
		
		@Override
		public boolean isValid(String str) {
			return BigUtils.isValidBigDecimal(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Complex.of(new BigDecimal(str));
		}
		
	},
	/** Accepts all {@link Complex#isValidInRectangularForm(String) valid} {@link Complex} numbers expressed in
	 * rectangular form. Does not accept fractions.*/
	COMPLEX_RECTANGULAR(makeIcon("3+2i", "Integer, decimal, or complex answers (in rectangular form) are allowed.")) {

		@Override
		public boolean isValid(String str) {
			return Complex.isValidInRectangularForm(str);
		}

		@Override
		public Complex parse(String str) {
			return Complex.of(str);
		}
		
	},
	/** Accepts all integers and all fractions. Fractions need not be simplified or proper.*/
	REAL_FRACTION(makeIcon("12/4", "Fractional and integer answers are allowed. Fractions do not need to be simplified "
			+ "or proper.")) {
		
		@Override
		public boolean isValid(String str) {
			return Fraction.isValid(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Fraction.of(str);
		}
		
	},
	REAL_PROPER_FRACTION(makeIcon("5/10", "Fractional answers are allowed. They do not need to be simplified, but they "
			+ "must be proper.")) {
		
		@Override
		public boolean isValid(String str) {
			return ProperFraction.isValid(str);
		}
		
		@Override
		public Complex parse(String str) {
			return ProperFraction.of(str);
		}
		
	},
	/** Accepts only {@link Fraction#isValidSimplified(String) simplified} fractions, which includes integers.
	 * The fractions do not need to be proper.*/
	REAL_SIMPLIFIED_FRACTION(makeIcon("5/3", "Fractional and integer answers are allowed. Fractions need to be in "
			+ "simplest form, but they do not need to be proper.")) {
		
		@Override
		public boolean isValid(String str) {
			return Fraction.isValidSimplified(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Fraction.of(str);
		}
		
	},
	REAL_PROPER_SIMPLIFIED_FRACTION(makeIcon("1/2", "Fractional answers are allowed. They must be proper and "
			+ "simplified.")) {
		
		@Override
		public boolean isValid(String str) {
			return ProperFraction.isValidSimplified(str);
		}
		
		@Override
		public Complex parse(String str) {
			return ProperFraction.of(str);
		}
		
	},
	/** Allows any {@link MixedNumber#isValid(String) valid} {@link MixedNumber} <b>as well as zero</b>. Note that
	 * zero is not usually considered to be a valid {@code MixedNumber}.*/
	MIXED_NUMBER(makeIcon("4 6/8", "Mixed numbers and integers allowed. The fractional part does not need to be "
			+ "simplified")) {

		@Override
		public boolean isValid(String str) {
			return MixedNumber.isValid(str);
		}

		@Override
		public Complex parse(String str) {
			return MixedNumber.of(str);
		}
		
	};
	
	private static final String ICON_TOOLTIP_CSS = "math-answer-icon-tooltip";
	private static final String ICON_CSS = "math-answer-icon";
	
	private static Label makeIcon(String text, String tooltip) {
		Label icon = new Label(text);
		icon.getStyleClass().add(ICON_CSS);
		Tooltip t = new Tooltip(tooltip);
		t.getStyleClass().add(ICON_TOOLTIP_CSS);
		icon.setTooltip(t);
		t.setShowDelay(Duration.millis(250));
		return icon;
	}
	
	private final Label icon;
	
	MathAnswerMode(Label icon) {
		this.icon = icon;
	}
	
	/** Returns {@code true} iff the given string represents a valid answer in this {@link MathAnswerMode}.*/
	public abstract boolean isValid(String str);
	
	/** Parses the given string to a {@link Complex} number. This method assumes that the given string is
	 * {@link #isValid(String) valid}.*/
	public abstract Complex parse(String str);
	
	public final Label icon() {
		return icon;
	}
	
	public boolean allowsNonReals() {
		return this == COMPLEX_RECTANGULAR;
	}
	
	/** Adapts the given {@link Complex} answer to one that would be {@link #isValid(String) valid} according to
	 * this {@link MathAnswerMode}.*/
	public String adapt(Complex answer) {
		if(!canAdapt(answer))
			throw new IllegalArgumentException(String.format("%s is not adaptable to this mode (%s)", answer, this));
		if(isFractionBased() && answer instanceof FractionConvertible) {
			FractionConvertible fc = (FractionConvertible) answer;
			return fc.toFraction().toParsableString();
		}
		else if(isDecimalBased())
			return BigUtils.toPrettyString(answer.toBigDecimalExact());
		else if(this == MIXED_NUMBER && answer instanceof MixedNumberConvertible) {
			MixedNumberConvertible mnc = (MixedNumberConvertible) answer;
			return mnc.toMixedNumber().toParsableString();
		}
		throw new UnsupportedOperationException(String.format("Need adaptation code for: %s", this));
	}
	
	public boolean canAdapt(Complex answer) {
		if(this == INTEGER)
			return answer.isInteger();
		if(this == REAL_DECIMAL)
			return answer.isRealAndExactlyRepresentable();
		if(this == COMPLEX_RECTANGULAR)
			return answer.isExactlyRepresentable();
		if(this == REAL_FRACTION || this == REAL_PROPER_FRACTION || this == REAL_SIMPLIFIED_FRACTION ||
				this == REAL_PROPER_SIMPLIFIED_FRACTION)
			return answer instanceof FractionConvertible;
		if(this == MIXED_NUMBER)
			return answer instanceof MixedNumberConvertible;
		throw new UnsupportedOperationException(String.format("this=%s", this));
	}
	
	public boolean isOneOf(MathAnswerMode first, MathAnswerMode... rest) {
		if(this == first)
			return true;
		for(MathAnswerMode mode : rest)
			if(this == mode)
				return true;
		return false;
	}
	
	/** Returns {@code true} iff the set of all possible strings that are {@link #isValid(String) valid} according
	 * to {@code mode} is a proper subset of the set of all possible strings that are valid according to
	 * {@code superset}.*/
	public boolean isProperSubsetOf(MathAnswerMode superset) {
		switch(this) {
			case INTEGER: return superset.isOneOf
					(REAL_DECIMAL, COMPLEX_RECTANGULAR, REAL_FRACTION, REAL_SIMPLIFIED_FRACTION, MIXED_NUMBER);
			case REAL_DECIMAL: return superset.isOneOf(COMPLEX_RECTANGULAR);
			case COMPLEX_RECTANGULAR: return false;
			case REAL_FRACTION: return false;
			case MIXED_NUMBER: return false;
			case REAL_PROPER_FRACTION: return superset.isOneOf(REAL_FRACTION);
			case REAL_SIMPLIFIED_FRACTION: return superset.isOneOf(REAL_FRACTION);
			case REAL_PROPER_SIMPLIFIED_FRACTION: return superset.isOneOf
					(REAL_PROPER_FRACTION, REAL_SIMPLIFIED_FRACTION, REAL_FRACTION);
			default: throw new UnsupportedOperationException(String.format("this=%s", this));
		}
	}
	
	/** Returns {@code true} iff {@code this} is one of the following:
	 * <ul>
	 * <li>{@link #REAL_FRACTION}</li>
	 * <li>{@link #REAL_PROPER_FRACTION}</li>
	 * <li>{@link #REAL_SIMPLIFIED_FRACTION}</li>
	 * <li>{@link #REAL_PROPER_SIMPLIFIED_FRACTION}</li>
	 * </ul>
	 * A {@link MathProblem} cannot have more than one fraction-based answer mode.
	 * */
	public final boolean isFractionBased() {
		return 	this == REAL_FRACTION || this == REAL_PROPER_FRACTION || this == REAL_SIMPLIFIED_FRACTION ||
				this == REAL_PROPER_SIMPLIFIED_FRACTION;
	}
	
	/** Returns {@code true} iff {@code this} is one of the following:
	 * <ul>
	 * <li>{@link #INTEGER}</li>
	 * <li>{@link #REAL_DECIMAL}</li>
	 * <li>{@link #COMPLEX_RECTANGULAR}</li>
	 * </ul>
	 * A {@link MathProblem} cannot have more than one decimal-based answer mode.
	 * */
	public final boolean isDecimalBased() {
		return 	this == INTEGER || this == REAL_DECIMAL || this == COMPLEX_RECTANGULAR;
	}
	
	public static void ensureValid(EnumSet<MathAnswerMode> modes, MathAnswerMode canonicalMode) {
		if(modes.isEmpty())
			throw new IllegalArgumentException("Set of modes must not be empty");
		if(!modes.contains(canonicalMode))
			throw new IllegalArgumentException("The set of MathAnswerModes does not contain the canonical mode");
		for(MathAnswerMode mode1 : modes)
			for(MathAnswerMode mode2 : modes)
				if(mode1 != mode2 && mode1.isProperSubsetOf(mode2))
					throw new IllegalArgumentException(String.format("Cannot have both %s and %s modes, since"
							+ "%1$s is a subset of %2$s.", mode1.name(), mode2.name()));
	}
	
}
