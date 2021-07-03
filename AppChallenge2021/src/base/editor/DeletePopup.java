package base.editor;

import base.graphics.FadePopup;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DeletePopup extends FadePopup {
	
	private static final String
			DELETE_POPUP_CSS = "delete-popup",
			SURE_CSS = "sure",
			INFO_CSS = "info",
			BUTTON_BAR_CSS = "button-bar",
			YES_CSS = "yes",
			NO_CSS = "no";
	
	private final VBox vBox;
	private final Label sure, info;
	private final HBox buttonBar;
	private final Button yes, no;
	
	public DeletePopup(StackPane over) {
		super(over);
		sure = new Label("Are you sure?");
		info = new Label("The current set will be permanently erased.");
		yes = new Button("Yes, delete the set");
		no = new Button("No, keep the set");
		buttonBar = new HBox(no, yes);
		vBox = new VBox(sure, info, buttonBar);
		initVBox();
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT / 2);
		getChildren().add(vBox);
		getStyleClass().add(DELETE_POPUP_CSS);
	}

	private void initVBox() {
		sure.getStyleClass().add(SURE_CSS);
		info.getStyleClass().add(INFO_CSS);
		initButtonBar();
	}
	
	private void initButtonBar() {
		buttonBar.getStyleClass().add(BUTTON_BAR_CSS);
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
