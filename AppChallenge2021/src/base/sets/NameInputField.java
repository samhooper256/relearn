/**
 * 
 */
package base.sets;

import base.Verifiable;
import javafx.scene.control.TextField;

/**
 * @author Sam Hooper
 *
 */
public class NameInputField extends TextField implements Verifiable {

	@Override
	public VerificationResult verify() {
		if(getText().isBlank())
			return VerificationResult.failure("Name must not be blank.");
		return VerificationResult.success();
	}
	
	public void setOnChange(Runnable r) {
		textProperty().addListener(x -> r.run());
	}
	
}
