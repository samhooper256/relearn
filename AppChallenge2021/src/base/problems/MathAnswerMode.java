package base.problems;

import java.math.BigDecimal;

import javafx.scene.control.Label;
import math.*;

public enum MathAnswerMode {
	REAL_FRACTION {
		
		private static final Label ICON = makeIcon("12/4");
		
		@Override
		public boolean isValid(String str) {
			return Fraction.isValid(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Fraction.of(str);
		}
		
		@Override
		public Label icon() {
			return ICON;
		}
		
	},
	REAL_DECIMAL {
		
		private static final Label ICON = makeIcon("1.5");
		
		@Override
		public boolean isValid(String str) {
			return BigUtils.isValidBigDecimal(str);
		}
		
		@Override
		public Complex parse(String str) {
			return Complex.of(new BigDecimal(str));
		}
		
		@Override
		public Label icon() {
			return ICON;
		}
		
	},
	COMPLEX_RECTANGULAR {

		private static final Label ICON = makeIcon("3+2i");
		
		@Override
		public boolean isValid(String str) {
			return Complex.isValidInRectangularForm(str);
		}

		@Override
		public Complex parse(String str) {
			return Complex.of(str);
		}

		@Override
		public Label icon() {
			return ICON;
		}
		
	};
	
	public abstract boolean isValid(String str);
	
	public abstract Complex parse(String str);
	
	public abstract Label icon();
	
	public boolean allowsComplex() {
		return this == COMPLEX_RECTANGULAR;
	}
	
	private static final String ICON_CSS = "math-answer-icon";
	
	private static Label makeIcon(String text) {
		Label icon = new Label(text);
		icon.getStyleClass().add(ICON_CSS);
		return icon;
	}
	
}
