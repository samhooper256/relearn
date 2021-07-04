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
 * <p>The {@link #min() minimum} allowable value of an {@code IntField} must be less than or equal to 1.
 * The {@link #max() maximum} allowable value must be non-negative (that is, 0 or positive).</p>
 * 
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	/** The {@link #intValue()} of this {@link IntField} when it is empty.*/
	public static final int EMPTY_VALUE = 0;
	private static final String INT_FIELD_CSS = "int-field";

	/** <p>Creates a new {@link IntField} with the given range.</p>
	 * <p>Unlike the constructors, this method never throws an {@link IllegalArgumentException}. Instead, if the
	 * {@code min} and {@code max} values specify an invalid range, they are adjusted to a valid range as follows:
	 * <ol><li>If {@code (min > max)}, then {@code min} is set equal to {@code max}.</li>
	 * <li>If {@code min} is greater than 1, it is set to 1.</li>
	 * <li>If {@code max} is less than 0, it is set to 0.</li></ol>*/
	public static IntField withRange(int min, int max) {
		if(min > max)
			min = max;
		min = Math.min(1, min);
		max = Math.max(0, max);
		return new IntField(min, max);
	}
	
	/** <p>Creates a new {@link IntField} with the given range.</p>
	 * <p>This method first adjusts the range as specified in {@link #withRange(int, int)}. Then, {@code startingValue}
	 * is possibly adjusted according to the following steps:
	 * <ol><li>If {@code startingValue} is less than the (possibly modified) {@code min}, it it set equal to the
	 * (possibly modified) {@code min}.</li><li>If {@code startingValue} is greater than the (possibly modified)
	 * {@code max}, it is set equal to the (possibly modified) {@code max}.</li></ol>
	 * The starting value of the returned {@code IntField} is then set to the (possibly modified)
	 * {@code startingValue)}.</p>*/
	public static IntField withRange(int min, int max, int startingValue) {
		IntField f = withRange(min, max);
		f.setValue(Basics.clamp(startingValue, f.min(), f.max()));
		return f;
	}
	
	private final IntegerProperty valueProperty;
	
	private final int min, max;
	
	/** @throws IllegalArgumentException if {@code (min > max || max < 0 || min > 1)}. */
	public IntField(int min, int max) {
		if(min > max)
			throw new IllegalArgumentException(String.format("min > max (%d > %d)", min, max));
		if(max < 0)
			throw new IllegalArgumentException(String.format("max must be non-negative (was: %d)", max));
		if(min > 1)
			throw new IllegalArgumentException(String.format("min must be less than or equal to 1 (was: %d)", min));
		this.min = min;
		this.max = max;
		valueProperty = new SimpleIntegerProperty(EMPTY_VALUE);
		init();
	}
	
	/** @throws IllegalArgumentException if {@code (min > max || max < 0 || min > 1)}. */
	public IntField(int min, int max, int startingValue) {
		this(min, max);
		setValue(startingValue);
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
			change.setRange(0, oldText.length());
			int val;
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
