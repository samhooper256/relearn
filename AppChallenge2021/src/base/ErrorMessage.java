/**
 * 
 */
package base;

import javafx.scene.control.Label;

/**
 * @author Sam Hooper
 *
 */
public class ErrorMessage extends Label {
	
	private static final String CSS = "error-message";
	
	public ErrorMessage(String text) {
		super(text);
		getStyleClass().add(CSS);
	}
}
