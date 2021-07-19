package base.sets;

import base.Main;
import base.graphics.MinimalisticFadePopup;
import base.settings.Settings;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class EditWarning extends MinimalisticFadePopup {
	
	private static final String
			EDIT_WARNING_CSS = "edit-warning",
			DO_NOT_SHOW_CSS = "do-not-show",
			CANCEL_BUTTON_CSS = "cancel",
			CONTINUE_BUTTON_CSS = "continue";
	
	private final Button cancelButton, continueButton;
	private final CheckBox doNotShow;
	
	private ProblemSet requestedSet;
	
	public EditWarning(StackPane over) {
		super(over, "Warning", "Editing a set will cause all of its stats to be erased.");
		doNotShow = new CheckBox("Don't show this warning again");
		initDoNotShow();
		cancelButton = new Button("Cancel");
		initCancelButton();
		continueButton = new Button("Continue");
		initContinueButton();
		buttonBar.getChildren().addAll(cancelButton, continueButton);
		getStyleClass().add(EDIT_WARNING_CSS);
	}

	private void initDoNotShow() {
		doNotShow.selectedProperty().bindBidirectional(Settings.get().doNotShowEditWarning());
		doNotShow.getStyleClass().add(DO_NOT_SHOW_CSS);
		extra.getChildren().add(doNotShow);
	}

	private void initCancelButton() {
		cancelButton.getStyleClass().add(CANCEL_BUTTON_CSS);
		cancelButton.setOnAction(e -> cancelAction());
	}
	
	private void cancelAction() {
		fadeOut();
	}
	
	private void initContinueButton() {
		continueButton.getStyleClass().add(CONTINUE_BUTTON_CSS);
		continueButton.setOnAction(e -> continueAction());
	}
	
	private void continueAction() {
		hidePopup();
		Main.scene().edit(requestedSet);
	}
	
	void requestEdit(ProblemSet set) {
		fadeIn();
		requestedSet = set;
	}
}
