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
 * 
 * <p>The {@link #min() minimum} allowable value of an {@code IntField} must be {@code 0} if the {@link #max() maximum}
 * allowable value is positive (or zero). The following
 * example demonstrates why: Suppose the minimum value was 5 and the maximum was 15. If the user wanted to type "12",
 * they would first need to type "1". However, the text "1" would not be allowable, since it is not in the valid range
 * of this {@code IntField}. Therefore, the user would not be able to (or would have to find a crafty way to) enter
 * completely valid inputs. This is not desirable. For a similar reason, the maximum value must be {@code 0} if the
 * minimum value is negative.</p>
 * 
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	/** The {@link #intValue()} of this {@link IntField} when it is empty.*/
	public static final int DEFAULT_EMPTY_VALUE = 0;
	
	private static final String INT_FIELD_CSS = "int-field";
	
	private final IntegerProperty valueProperty;
	
	private final int min, max;
	
	private int emptyValue;
	
	public IntField(int min, int max) {
		if(min > max)
			throw new IllegalArgumentException(String.format("min > max (%d > %d)", min, max));
		if(max >= 0) {
			if(min != 0)
				throw new IllegalArgumentException(String.format("min must be 0 (was %d) if max is positive %d", min));
		}
		else if(min < 0) {
			if(max != 0)
				throw new IllegalArgumentException(String.format("max must be 0 (was %d) if min is negative", max));
		}
		this.min = min;
		this.max = max;
		valueProperty = new SimpleIntegerProperty(DEFAULT_EMPTY_VALUE);
		emptyValue = DEFAULT_EMPTY_VALUE;
		init();
	}
	
	public IntField(int min, int max, int startingValue, int emptyValue) {
		this(min, max);
		setValue(startingValue);
		this.emptyValue = emptyValue;
	}
	
	private void init() {
		initFormatter();
		initValueProperty();
		getStyleClass().add(INT_FIELD_CSS);
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
				Parsing.isint(cleanedNewText) && (val = Integer.parseInt(cleanedNewText)) >= min() && val <= max())
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
				valueProperty.set(emptyValue);
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
	
	private void setValue(int newValue) {
		if(newValue < min() || newValue > max())
			throw new IllegalArgumentException(String.format("newValue (%d) out of range", newValue));
		setText(String.valueOf(newValue));
	}
	
	public int min() {
		return min;
	}
	
	public int max() {
		return max;
	}
	
	public boolean isEmpty() {
		return getText().isEmpty();
	}
	
}
