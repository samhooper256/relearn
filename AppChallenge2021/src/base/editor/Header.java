package base.editor;

import base.graphics.BackArrow;
import fxutils.PolarizedPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Sam Hooper
 *
 */
public class Header extends PolarizedPane {
	
	private static final String TITLE = "Set Editor";
	private static final String
			HEADER_CSS = "header",
			LEFT_CSS = "left",
			TITLE_CSS = "title";
	
	private final HBox left;
	private final BackArrow backArrow;
	private final Label title;
	private final DeleteSetButton deleteButton;
	
	public Header() {
		backArrow = new BackArrow();
		title = new Label(TITLE);
		left = new HBox(backArrow, title);
		initLeft();
		
		deleteButton = new DeleteSetButton();
		
		getStyleClass().add(HEADER_CSS);
		setLeft(left);
		setRight(deleteButton);
	}
	
	private void initLeft() {
		left.getStyleClass().add(LEFT_CSS);
		backArrow.setOnAction(() -> EditorPane.get().backArrowAction());
		title.getStyleClass().add(TITLE_CSS);
	}

}
