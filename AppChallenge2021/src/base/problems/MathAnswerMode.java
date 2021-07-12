package base.problems;

import java.math.BigDecimal;

import javafx.scene.control.*;
import javafx.util.Duration;
import math.*;

public enum MathAnswerMode {
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
	COMPLEX_RECTANGULAR(makeIcon("3+2i", "Integer, decimal, or complex answers (in rectangular form) are allowed.")) {

		@Override
		public boolean isValid(String str) {
			return Complex.isValidInRectangularForm(str);
		}

		@Override
		public Complex parse(String str) {
			return Complex.of(str);
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
	
}
