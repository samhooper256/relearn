package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import javafx.scene.control.*;
import javafx.util.Duration;
import math.*;
import utils.Colls;

public enum MathAnswerMode {
	/** Accepts all integers, possibly with a leading sign.*/
	INTEGER(makeIcon("7", "Integer answers are allowed.")) {
		
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
	/** Accepts only {@link Fraction#isValidSimplified(String) simplified} fractions.*/
	REAL_SIMPLIFIED_FRACTION(makeIcon("3/4", "Fractional answers are allowed. They need to be in simplest form, but "
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
	
	public abstract boolean isValid(String str);
	
	public abstract Complex parse(String str);
	
	public final Label icon() {
		return icon;
	}
	
	public boolean allowsComplex() {
		return this == COMPLEX_RECTANGULAR;
	}
	
	public static void ensureValid(EnumSet<MathAnswerMode> modes) {
		if(modes.isEmpty())
			throw new IllegalArgumentException("Set of modes must not be empty");
		if(modes.contains(REAL_FRACTION) && modes.contains(REAL_SIMPLIFIED_FRACTION))
			throw new IllegalArgumentException("Cannot have both REAL_FRACTION and REAL_SIMPLIFIED_FRACTION modes");
		if(Colls.countContains(modes, INTEGER, REAL_DECIMAL, COMPLEX_RECTANGULAR) > 1)
			throw new IllegalArgumentException("Cannot have both REAL_DECIMAL and COMPLEX_RECTANGULAR modes");
	}
	
}
