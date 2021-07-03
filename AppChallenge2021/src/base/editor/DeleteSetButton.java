package base.editor;

import base.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public final class DeleteSetButton extends Button {
	
	private static final String
			DELETE_SET_BUTTON_CSS = "delete-set-button",
			TEXT_CSS = "text";
	
	private final StackPane graphic;
	private final VBox vBox;
	private final Label text;
	private final ImageView trashCanView;
	
	public DeleteSetButton() {
		trashCanView = new ImageView(Main.TRASH_CAN_IMAGE);
		text = new Label("Delete Set");
		vBox = new VBox(text, trashCanView);
		initVBox();
		graphic = new StackPane(vBox);
		
		setGraphic(graphic);
		getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, DELETE_SET_BUTTON_CSS);
		setOnAction(() -> EditorPane.get().deleteSetButonAction());
	}
	
	private void initVBox() {
		vBox.setAlignment(Pos.CENTER);
		text.getStyleClass().add(TEXT_CSS);
	}
	
	public void setOnAction(Runnable r) {
		setOnAction(e -> r.run());
	}
	
}
