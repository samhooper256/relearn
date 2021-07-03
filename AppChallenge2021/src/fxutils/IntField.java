/**
 * 
 */
package fxutils;

import java.util.function.UnaryOperator;

import javafx.beans.property.*;
import javafx.scene.control.*;
import utils.*;

/**
 * <p>A {@link TextField} that only allows integers to be typed.</p>
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	/** The {@link #intValue()} of this {@link IntField} when it is empty.*/
	public static final int EMPTY_VALUE = 0;
	
	private static final String INT_FIELD_CSS = "int-field";
	
	private final SimpleIntegerProperty valueProperty;
	
	private int min, max;
	
	public IntField(int minValue, int maxValue) {
		this.min = minValue;
		this.max = maxValue;
		initFormatter();
		valueProperty = new SimpleIntegerProperty();
		initValueProperty();
		getStyleClass().add(INT_FIELD_CSS);
	}
	
	public IntField(int minValue, int maxValue, int startingValue) {
		this(minValue, maxValue);
		setValue(startingValue);
	}

	private void initFormatter() {
		UnaryOperator<TextFormatter.Change> op = change -> {
			String oldText = getText();
			String newText = Text.applyChange(oldText, change);
			if(oldText.equals(newText))
				return change;
			String cleanedNewText = Strings.keepIf(newText, Parsing::isDigitOrSign);
			int val;
			change.setRange(0, oldText.length());
			if(	cleanedNewText.isEmpty() ||
				Parsing.isint(cleanedNewText) && (val = Integer.parseInt(cleanedNewText)) >= min && val <= max)
				change.setText(cleanedNewText);
			else
				change.setText(oldText);
			return change;
		};
		this.setTextFormatter(new TextFormatter<>(op));
	}
	
	private void initValueProperty() {
		textProperty().addListener((x, y, z) -> {
			if(hasValidInt())
				valueProperty.set(parseText());
			else //it may not have a valid int if the box is empty.
				valueProperty.set(EMPTY_VALUE);
		});
	}
	
	private boolean hasValidInt() {
		return Parsing.isint(getText());
	}
	
	private int parseText() {
		return Integer.parseInt(getText());
	}
	
	public int intValue() {
		return valueProperty().get();
	}
	
	public ReadOnlyIntegerProperty valueProperty() {
		return valueProperty;
	}
	
	public void setValue(int newValue) {
		if(newValue < min || newValue > max)
			throw new IllegalArgumentException(String.format("newValue (%d) out of range", newValue));
		setText(String.valueOf(newValue));
	}
	
	public int max() {
		return max;
	}
	
	public int min() {
		return min;
	}
	
	public void setMin(int newMin) {
		min = newMin;
	}
	
	public void setMax(int newMax) {
		max = newMax;
	}
	
	public boolean isEmpty() {
		return getText().isEmpty();
	}
	
}
