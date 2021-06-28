/**
 * 
 */
package fxutils;

import javafx.scene.control.TextFormatter;

/**
 * @author Sam Hooper
 *
 */
public final class Text {
	
	private Text() {
		
	}
	
	public static boolean isDeletion(TextFormatter.Change change) {
		return change.getText().isEmpty();
	}
	
	public static boolean isAddition(TextFormatter.Change change) {
		return change.getRangeStart() == change.getRangeEnd();
	}
	
	public static boolean isReplacement(TextFormatter.Change change) {
		return !change.getText().isEmpty() && change.getRangeStart() != change.getRangeEnd();
	}
	
	public static String applyChange(String original, TextFormatter.Change change) {
		if(isDeletion(change))
			return original.substring(0, change.getRangeStart()) + original.substring(change.getRangeEnd());
		else
			return 	original.substring(0, change.getRangeStart()) + change.getText() +
					original.substring(change.getRangeEnd());
	}
}
