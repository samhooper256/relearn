/**
 * 
 */
package fxutils;

import java.util.function.UnaryOperator;

import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import utils.*;

/**
 * <p>A {@link TextField} that only allows non-negative integers to be typed.</p>
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	private final SimpleIntegerProperty valueProperty;
	private final int maxDigits;
	
	public IntField(int maxDigits) {
		this.maxDigits = maxDigits;
		setAlignment(Pos.CENTER);
		initFormatter();
		valueProperty = new SimpleIntegerProperty();
		initValueProperty();
	}

	private void initFormatter() {
		UnaryOperator<TextFormatter.Change> op = change -> {
			String after = Text.applyChange(getText(), change);
			String afterDigits = Strings.removeNonDigits(after);
			change.setRange(0, getText().length());
			change.setText(afterDigits.substring(0, Math.min(afterDigits.length(), maxDigits)));
			return change;
		};
		this.setTextFormatter(new TextFormatter<>(op));
	}
	
	private void initValueProperty() {
		textProperty().addListener((x, y, z) -> {
			if(hasValidInt())
				valueProperty.set(parseText());
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
	
}
