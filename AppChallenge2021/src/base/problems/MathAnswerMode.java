package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import javafx.scene.control.*;
import javafx.util.Duration;
import math.*;
import utils.Colls;

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
	REAL_FRACTION(makeIcon("12/4", "Fractional answers are allowed. They do not need to be simplified or proper.")) {
		
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
	/** Accepts only {@link Fraction#isValidSimplified(String) simplified} fractions. The fractions do not need to be
	 * proper.*/
	REAL_SIMPLIFIED_FRACTION(makeIcon("5/3", "Fractional answers are allowed. They need to be in simplest form, but "
			+ "they do not need to be proper.")) {
		
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
		if(isFractionBased() && answer instanceof FractionConvertible fc)
			return fc.toFraction().toParsableString();
		else if(isDecimalBased())
			return BigUtils.toPrettyString(answer.toBigDecimalExact());
		else if(this == MIXED_NUMBER && answer instanceof MixedNumberConvertible mnc)
			return mnc.toMixedNumber().toParsableString();
		throw new UnsupportedOperationException(String.format("Need adaptation code for: %s", this));
	}
	
	boolean canAdapt(Complex answer) {
		return switch(this) {
			case INTEGER -> answer.isInteger();
			case REAL_DECIMAL -> answer.isRealAndExactlyRepresentable();
			case COMPLEX_RECTANGULAR -> answer.isExactlyRepresentable();
			case REAL_FRACTION, REAL_PROPER_FRACTION, REAL_SIMPLIFIED_FRACTION, REAL_PROPER_SIMPLIFIED_FRACTION ->
					answer instanceof FractionConvertible;
			case MIXED_NUMBER -> answer instanceof MixedNumberConvertible;
		};
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
		if(Colls.countSatisfying(modes, MathAnswerMode::isFractionBased) > 1)
			throw new IllegalArgumentException("Cannot have more than one fraction-based mode");
		if(Colls.countSatisfying(modes, MathAnswerMode::isDecimalBased) > 1)
			throw new IllegalArgumentException("Cannot have more than one decimal-based mode");
		if(!modes.contains(canonicalMode))
			throw new IllegalArgumentException("The set of MathAnswerModes does not contain the canonical mode");
	}
	
}
