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
 * <p>A {@link TextField} that only allows numbers to be typed.</p>
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	private final SimpleIntegerProperty valueProperty;
	
	public IntField(int width) {
		setPrefWidth(width);
		setAlignment(Pos.CENTER);
		initFormatter();
		valueProperty = new SimpleIntegerProperty();
		initValueProperty();
	}

	private void initFormatter() {
		UnaryOperator<TextFormatter.Change> op = change -> {
			int start = change.getRangeStart(), end = change.getRangeEnd();
			boolean rangeIsEmpty = start == end;
			String newText = change.getText();
			if(!newText.isEmpty()) {
				if(rangeIsEmpty) { //we added some text
					change.setText(Strings.removeNonDigits(newText));
				}
				else { //we replaced some text
					String oldText = IntField.this.getText(start, end);
					if(Strings.containsNonDigit(newText))
						change.setText(oldText);
					else
						change.setText(newText);
				}
			}
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
