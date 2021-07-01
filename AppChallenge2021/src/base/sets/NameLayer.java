package base.sets;

import base.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class NameLayer extends VBox implements Verifiable {
	
	private static final String
			NAME_LAYER_CSS = "name-layer",
			ROW_CSS = "row";
	
	private final HBox row;
	private final ErrorMessage error;
	private final Label label;
	private final NameInputField field;
	
	public NameLayer() {
		label = new Label("Name:");
		field = new NameInputField();
		row = new HBox(label, field);
		initRow();
		
		error = new ErrorMessage("");
		initError();
		
		getChildren().addAll(row, error);
		getStyleClass().add(NAME_LAYER_CSS);
	}

	private void initRow() {
		row.getStyleClass().add(ROW_CSS);
		field.setOnChange(this::hideError);
	}
	
	private void initError() {
		error.setVisible(false);
	}
	
	public String name() {
		return field.getText().strip();
	}
	
	public void setName(String name) {
		field.setText(name);
	}
	
	private void showError(String message) {
		error.setText(message);
		error.setVisible(true);
	}
	
	public void hideError() {
		error.setVisible(false);
	}

	@Override
	public VerificationResult verify() {
		VerificationResult result = field.verify();
		if(result.isFailure())
			showError(result.errorMessage());
		return result;
	}
	
}
