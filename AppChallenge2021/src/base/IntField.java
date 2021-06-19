/**
 * 
 */
package base;

import java.util.function.UnaryOperator;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import utils.*;

/**
 * @author Sam Hooper
 *
 */
public class IntField extends TextField {
	
	public IntField(int width) {
		this(width, true);
	}
	
	public IntField(int width, boolean center) {
		setPrefWidth(width);
		if(center)
			setAlignment(Pos.CENTER);
		initFormatter();
		
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
	
	public boolean hasValidInt() {
		return Parsing.isint(getText());
	}
	
	
	
}
