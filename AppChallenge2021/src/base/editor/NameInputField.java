/**
 * 
 */
package base.editor;

import base.*;
import base.sets.ProblemSet;
import javafx.scene.control.TextField;

/**
 * @author Sam Hooper
 *
 */
public class NameInputField extends TextField implements Verifiable {
	
	private static final String NAME_INPUT_FIELD_CSS = "name-input-field";
	
	public NameInputField() {
		setPromptText("Enter name...");
		getStyleClass().add(NAME_INPUT_FIELD_CSS);
	}
	
	@Override
	public VerificationResult verify(Object... context) { //TODO disallow duplicate set names!
		String originalName = (String) context[0], text = getText().strip();
		if(text.isEmpty())
			return VerificationResult.failure("Name must not be blank");
		if(!text.equals(originalName) && ProblemSet.isInUse(text))
			return VerificationResult.failure("A set with that name already exists");
		return VerificationResult.success();
	}
	
	public void setOnChange(Runnable r) {
		textProperty().addListener(x -> r.run());
	}
	
}
