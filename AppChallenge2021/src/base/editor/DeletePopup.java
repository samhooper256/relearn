package base.editor;

import base.graphics.MinimalisticFadePopup;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DeletePopup extends MinimalisticFadePopup {
	
	private static final String
			DELETE_POPUP_CSS = "delete-popup",
			YES_CSS = "yes",
			NO_CSS = "no";
	
	private final Button yes, no;
	
	public DeletePopup(StackPane over) {
		super(over, "Are you sure?", "The current set will be permanently erased.");
		yes = new Button("Yes, delete the set");
		no = new Button("No, keep the set");
		initButtonBar();
		getStyleClass().add(DELETE_POPUP_CSS);
	}

	private void initButtonBar() {
		buttonBar.getChildren().addAll(no, yes);
		initYes();
		initNo();
	}

	private void initYes() {
		yes.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, YES_CSS);
		yes.setOnAction(e -> yesAction());
	}
	
	private void yesAction() {
		EditorPane.get().deleteCurrentSet();
	}
	
	private void initNo() {
		no.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, NO_CSS);
		no.setOnAction(e -> noAction());
	}
	
	private void noAction() {
		fadeOut();
	}
	
}
